# Antigravity CLI → Chat UI Wrapper
## Architecture & PRD v1

---

## 1. Goal

Wrap the `agy` (Antigravity CLI) terminal agent inside a native Android chat interface. User interacts entirely through chat bubbles (never sees raw terminal), including tapping Yes/No on permission prompts, while `agy` continues running normally underneath via Termux/proot.

---

## 2. Confirmed Technical Findings

These were established through live research + hands-on PTY capture testing (not assumptions):

### CLI behavior
- `agy` is TUI-first (full-screen scrollable conversation pane + status bar). A documented `--output-format json` mode exists in some sources but a separate operator-guide source explicitly states there is **no structured JSON result envelope** — treat this as unreliable/unconfirmed, do not depend on it.
- `agy -p` / `--print` in non-TTY contexts (plain subprocess + pipe) **hangs indefinitely or silently drops stdout** — publicly tracked bug (GitHub issue #318). A real PTY is not optional; it's the only reliable way to run `agy` headlessly/programmatically.
- A **real PTY requires explicit terminal size** (`TIOCSWINSZ`) at spawn, plus `SIGWINCH` forwarding on resize. Without this, `agy` renders nothing (confirmed: blank black screen in our first test). Fixed in our capture script by reading current winsize via `TIOCGWINSZ` and applying it to the child PTY before/after spawn, and re-applying on every `SIGWINCH`.
- `agy` persists structured artifacts to disk independent of terminal output:
  `~/.gemini/antigravity/brain/<conversation-GUID>/task.md`, `implementation_plan.md`, `walkthrough.md`, each with a paired `.metadata.json`. This is a secondary, more structured data source we can use alongside terminal parsing.
- Session resume hint is printed after each turn: `agy --conversation=<uuid>` — gives us the conversation GUID for free, useful both for session persistence and for locating the matching `brain/<GUID>/` artifact folder.

### Confirmed output patterns (from real capture — `agy_clean_*.log`)

| Element | Pattern |
|---|---|
| Tool call | `• ActionName(args)` e.g. `• Create(/root/index.html)`, `• Read(/root/index.html) (ctrl+o to expand)`, `• ListDir(/root)` |
| Thinking block | `► Thought for Ns, N tokens` followed by a short summary line |
| Diff preview (collapsed) | Lines like `N + <content>`, ending with `... N more lines (f for full diff)` |
| Permission prompt | A line ending in `?`, followed by numbered options (`1. Yes, allow creation` / `2. No, deny creation`), current selection marked with a `>` prefix. Navigated with ↑/↓, confirmed with Enter. **Not plain y/n text input.** |
| Status/model line | `<Model name> · <effort level>` e.g. `Gemini 3.6 Flash · high` |
| Session resume hint | `agy --conversation=<uuid>` |

### Important gotcha discovered
Naive linear ANSI-stripping (strip escape codes, concatenate bytes) produces **duplicate-line noise** on any redraw/spinner sequence — our own capture shows `Loading...`, `Loadin`, `Loading.` as three separate lines, because the raw byte stream contains cursor-reposition + overwrite sequences that a linear stripper can't collapse. Raw stream ≠ what a human actually sees on screen. This must be solved properly (see §3) before the parser can be trusted in production.

---

## 3. Core Architecture Decision: Real Terminal Emulation, Not Regex Stripping

**Decision:** Do not extend the naive ANSI-stripper from the test script into production. Instead, feed the raw PTY byte stream into an actual VT100/xterm-compatible terminal emulator that maintains a virtual screen buffer (cursor position, redraw/overwrite handling, scrollback).

**Why:** Only a real emulator correctly resolves cursor-controlled redraws (spinners, progress updates, the permission-prompt's own arrow-navigation highlight) into the same clean state a human sees. Regex-stripping the raw byte stream, as proven above, leaks intermediate redraw frames as duplicate/garbage lines.

**Practical path:** Termux's own terminal-emulator library (`com.termux.terminal` — open source, MIT-licensed, already battle-tested for exactly this VT100/xterm emulation job) is the natural fit and is presumably already available given the existing Flutter Studio terminal-bridge module. Reuse it here rather than writing a custom emulator.

**Parsing model:**
1. Feed raw bytes into the emulator continuously.
2. After each burst of writes, wait for a short idle gap ("settle" — e.g. ~80–150ms with no new bytes) and snapshot the emulator's current screen buffer.
3. Diff the new snapshot against the last stable snapshot → emit only the *net new, stable* lines.
4. Run the semantic parser (§4.3) only against these stable line-deltas, never against raw bytes.

This eliminates the duplicate-spinner-line problem at the source, rather than trying to regex it away after the fact.

---

## 4. System Architecture

```
┌─────────────────────────────────────────────────────────┐
│  Termux / proot Ubuntu                                    │
│  agy process  ⇄  PTY master/slave                         │
└───────────────────────┬───────────────────────────────────┘
                         │ raw bytes (bidirectional)
┌───────────────────────▼───────────────────────────────────┐
│ 1. PTY Bridge Service                                      │
│    - spawn agy with correct initial winsize (TIOCSWINSZ)   │
│    - forward SIGWINCH → re-apply winsize on resize          │
│    - reuse existing Flutter Studio terminal-bridge module   │
└───────────────────────┬───────────────────────────────────┘
                         │ raw bytes
┌───────────────────────▼───────────────────────────────────┐
│ 2. Terminal Emulation Layer                                 │
│    - VT100/xterm emulator (Termux terminal-emulator lib)    │
│    - virtual screen buffer, idle-settle snapshot + diff      │
│    - output: clean, stable line-delta events                │
└───────────────────────┬───────────────────────────────────┘
                         │ clean line deltas
┌───────────────────────▼───────────────────────────────────┐
│ 3. Semantic Parser / Event Extractor                         │
│    - rule engine over clean lines (§2 patterns)              │
│    - emits typed events: AssistantText, ToolCall,            │
│      ThinkingBlock, DiffPreview, PermissionPrompt,            │
│      StatusLine, SessionResumeHint                            │
└──────────┬────────────────────────────────┬─────────────────┘
           │                                 │
┌──────────▼───────────┐         ┌───────────▼───────────────┐
│ 4. Artifact Watcher    │         │ 5. Chat Renderer (UI)      │
│  FileObserver on        │         │  bubble types per §5       │
│  ~/.gemini/antigravity/ │         │                            │
│  brain/<GUID>/           │         └───────────┬───────────────┘
│  parses task/plan/       │                     │
│  walkthrough .md          │                     │
│  + metadata.json           │        ┌───────────▼───────────────┐
└──────────────────────────┘         │ 6. Action Injector          │
                                       │  on button tap:              │
                                       │  write ↑/↓ escape seqs to    │
                                       │  reach target option index,  │
                                       │  then \r, into PTY master     │
                                       └───────────────────────────────┘
```

---

## 5. Chat Bubble Spec (v1)

| Bubble type | Source | Trigger | Render |
|---|---|---|---|
| Assistant text | Parser | Plain prose lines outside other patterns | Standard chat bubble, markdown-rendered |
| Tool call card | Parser | `• ActionName(args)` | Collapsed pill: icon + action + target path; expandable |
| Thinking (collapsible) | Parser | `► Thought for Ns, N tokens` + summary | Collapsed "thinking..." row, tap to expand summary |
| Diff card | Parser | `N + <content>` block ending in `... N more lines` | Code-diff view, expandable to full diff |
| Permission prompt card | Parser | Line ending `?` + numbered options + `>` cursor | Inline Yes/No (or full option list) buttons |
| Plan / Walkthrough card | Artifact watcher | `implementation_plan.md` / `walkthrough.md` change | Rich markdown card, separate from main chat stream |
| Status strip | Parser | `<Model> · <effort>` line | Persistent header, not a bubble |

---

## 6. Permission Handling UX Flow

1. Parser detects a `PermissionPrompt` event: question text + ordered option list + current highlighted index (from `>` position).
2. Renderer shows buttons for each option (typically Yes/No, but must render whatever options are actually present — do not hardcode to exactly 2).
3. On tap: Action Injector computes `delta = target_index - current_highlighted_index`, writes that many `\x1b[B` (down) or `\x1b[A` (up) sequences into the PTY master fd, then writes `\r`.
4. **Open question (needs verification):** default highlighted index on prompt appearance — our sample showed cursor starting at option 1 ("Yes"), but this may not hold for all prompt types (e.g. a destructive action might default to "No"). Injector must always compute delta from the *parsed* current index, never assume index 0.

---

## 7. Session Persistence

- Capture the `agy --conversation=<uuid>` resume hint after each turn.
- Store `conversation_uuid` per chat session in Room DB.
- On app relaunch for an existing chat, respawn `agy` with `--conversation=<uuid> -c` instead of starting fresh.
- Use the same UUID to locate `~/.gemini/antigravity/brain/<uuid>/` for the Artifact Watcher.

---

## 8. Known Risks / Open Items

- **Terminal-emulator integration is the highest-effort item** — correctly wiring Termux's terminal-emulator library to produce idle-settle snapshots is non-trivial and is the main technical risk for the whole project.
- Default cursor position on permission prompts unverified across all prompt types (see §6).
- Footer hint text under prompts is inconsistent between captures (`enter Confirm` vs `tab Amend · f full diff`) — confirmed unreliable, detection correctly does **not** depend on it (uses `?` + numbered options + `>` instead).
- `~/.gemini/antigravity/brain/` path structure is undocumented/unofficial and could change across `agy` versions without notice.
- Multi-session / multiple simultaneous `agy` processes — out of scope for v1, flagged for v2.

---

## 9. Tech Stack (reuse from Flutter Studio)

Kotlin native, MVVM + Clean Architecture, Hilt, Room (chat history + session UUIDs), existing Termux/proot terminal-bridge module for the PTY layer, Termux terminal-emulator library for VT100 emulation, Material 3 UI.

---

## 10. MVP Build Order

1. ✅ PTY bridge + correct winsize handling (validated in hands-on testing)
2. Integrate Termux terminal-emulator lib → idle-settle snapshot + diff pipeline
3. Semantic parser for the 4 core event types (tool call, thinking, diff, permission prompt)
4. Basic chat UI rendering (assistant text + tool call cards only)
5. Permission prompt card + arrow-key Action Injector
6. Artifact Watcher → Plan/Walkthrough cards (v1.1)
7. Session resume via `--conversation` flag (v1.1)

---

## 11. Project Structure

Working name: **AgyChat**. Package: `com.agychat.app`. Kotlin, MVVM + Clean Architecture (domain / data / presentation), Hilt, Room, Jetpack Compose (chat-bubble UI benefits from Compose's list/animation model over XML Views — deviates from Flutter Studio's XML choice intentionally, matches the CloudCode AI precedent instead).

Structure is broken down to maximum reasonable granularity — one file per model, per use case, per pattern matcher, per composable — so generation can proceed folder-by-folder per the usual workflow.

```
AgyChat/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/agychat/app/
│       │   │   ├── AgyChatApplication.kt
│       │   │   ├── MainActivity.kt
│       │   │   │
│       │   │   ├── di/                                    (Hilt DI modules)
│       │   │   │   ├── AppModule.kt
│       │   │   │   ├── DatabaseModule.kt
│       │   │   │   ├── RepositoryModule.kt
│       │   │   │   ├── PtyBridgeModule.kt
│       │   │   │   ├── TerminalEmulatorModule.kt
│       │   │   │   ├── ParserModule.kt
│       │   │   │   ├── ArtifactWatcherModule.kt
│       │   │   │   ├── DispatcherModule.kt
│       │   │   │   └── UseCaseModule.kt
│       │   │   │
│       │   │   ├── domain/                                (pure Kotlin, no Android deps)
│       │   │   │   ├── model/
│       │   │   │   │   ├── ChatSession.kt
│       │   │   │   │   ├── ChatMessage.kt
│       │   │   │   │   ├── MessageType.kt
│       │   │   │   │   ├── AssistantTextContent.kt
│       │   │   │   │   ├── ToolCallContent.kt
│       │   │   │   │   ├── ToolCallStatus.kt
│       │   │   │   │   ├── ThinkingBlockContent.kt
│       │   │   │   │   ├── DiffPreviewContent.kt
│       │   │   │   │   ├── DiffLine.kt
│       │   │   │   │   ├── DiffLineType.kt
│       │   │   │   │   ├── PermissionPromptContent.kt
│       │   │   │   │   ├── PermissionOption.kt
│       │   │   │   │   ├── PermissionResponse.kt
│       │   │   │   │   ├── StatusLineContent.kt
│       │   │   │   │   ├── PlanArtifact.kt
│       │   │   │   │   ├── WalkthroughArtifact.kt
│       │   │   │   │   ├── TaskArtifact.kt
│       │   │   │   │   ├── ArtifactMetadata.kt
│       │   │   │   │   ├── ArtifactType.kt
│       │   │   │   │   ├── TerminalScreenSnapshot.kt
│       │   │   │   │   ├── TerminalLineDelta.kt
│       │   │   │   │   ├── ParsedEvent.kt
│       │   │   │   │   ├── ParsedEventType.kt
│       │   │   │   │   ├── PtyConnectionState.kt
│       │   │   │   │   ├── PtySize.kt
│       │   │   │   │   ├── AgentModelInfo.kt
│       │   │   │   │   ├── SessionResumeInfo.kt
│       │   │   │   │   └── ProjectWorkspace.kt
│       │   │   │   │
│       │   │   │   ├── repository/                        (interfaces only)
│       │   │   │   │   ├── ChatSessionRepository.kt
│       │   │   │   │   ├── ChatMessageRepository.kt
│       │   │   │   │   ├── PtyBridgeRepository.kt
│       │   │   │   │   ├── ArtifactRepository.kt
│       │   │   │   │   ├── WorkspaceRepository.kt
│       │   │   │   │   └── SettingsRepository.kt
│       │   │   │   │
│       │   │   │   └── usecase/
│       │   │   │       ├── session/
│       │   │   │       │   ├── CreateChatSessionUseCase.kt
│       │   │   │       │   ├── GetChatSessionsUseCase.kt
│       │   │   │       │   ├── GetChatSessionByIdUseCase.kt
│       │   │   │       │   ├── DeleteChatSessionUseCase.kt
│       │   │   │       │   ├── RenameChatSessionUseCase.kt
│       │   │   │       │   └── ResumeChatSessionUseCase.kt
│       │   │   │       ├── message/
│       │   │   │       │   ├── SendUserMessageUseCase.kt
│       │   │   │       │   ├── ObserveChatMessagesUseCase.kt
│       │   │   │       │   ├── AppendParsedEventAsMessageUseCase.kt
│       │   │   │       │   └── ClearChatHistoryUseCase.kt
│       │   │   │       ├── pty/
│       │   │   │       │   ├── StartAgyProcessUseCase.kt
│       │   │   │       │   ├── StopAgyProcessUseCase.kt
│       │   │   │       │   ├── WriteRawInputToPtyUseCase.kt
│       │   │   │       │   ├── ObservePtyOutputUseCase.kt
│       │   │   │       │   ├── ObservePtyConnectionStateUseCase.kt
│       │   │   │       │   ├── SyncPtyWindowSizeUseCase.kt
│       │   │   │       │   └── ResizePtyOnConfigChangeUseCase.kt
│       │   │   │       ├── terminal/
│       │   │   │       │   ├── FeedBytesToEmulatorUseCase.kt
│       │   │   │       │   ├── SnapshotStableScreenUseCase.kt
│       │   │   │       │   ├── DiffScreenSnapshotsUseCase.kt
│       │   │   │       │   └── ResetEmulatorStateUseCase.kt
│       │   │   │       ├── parser/
│       │   │   │       │   ├── ParseLineDeltaUseCase.kt
│       │   │   │       │   ├── DetectToolCallUseCase.kt
│       │   │   │       │   ├── DetectThinkingBlockUseCase.kt
│       │   │   │       │   ├── DetectDiffPreviewUseCase.kt
│       │   │   │       │   ├── DetectPermissionPromptUseCase.kt
│       │   │   │       │   ├── DetectStatusLineUseCase.kt
│       │   │   │       │   ├── DetectSessionResumeHintUseCase.kt
│       │   │   │       │   └── ClassifyPlainAssistantTextUseCase.kt
│       │   │   │       ├── permission/
│       │   │   │       │   ├── ComputeArrowNavigationDeltaUseCase.kt
│       │   │   │       │   ├── SendPermissionResponseUseCase.kt
│       │   │   │       │   └── BuildArrowEscapeSequenceUseCase.kt
│       │   │   │       ├── artifact/
│       │   │   │       │   ├── StartArtifactWatcherUseCase.kt
│       │   │   │       │   ├── StopArtifactWatcherUseCase.kt
│       │   │   │       │   ├── ParseTaskArtifactUseCase.kt
│       │   │   │       │   ├── ParsePlanArtifactUseCase.kt
│       │   │   │       │   ├── ParseWalkthroughArtifactUseCase.kt
│       │   │   │       │   ├── ParseArtifactMetadataUseCase.kt
│       │   │   │       │   └── LocateBrainFolderForConversationUseCase.kt
│       │   │   │       ├── workspace/
│       │   │   │       │   ├── CreateWorkspaceUseCase.kt
│       │   │   │       │   ├── GetWorkspacesUseCase.kt
│       │   │   │       │   ├── SelectActiveWorkspaceUseCase.kt
│       │   │   │       │   └── DeleteWorkspaceUseCase.kt
│       │   │   │       └── settings/
│       │   │   │           ├── GetAgyBinaryPathUseCase.kt
│       │   │   │           ├── SetAgyBinaryPathUseCase.kt
│       │   │   │           ├── GetIdleSettleDelayUseCase.kt
│       │   │   │           ├── SetIdleSettleDelayUseCase.kt
│       │   │   │           └── ValidateAgyInstallationUseCase.kt
│       │   │   │
│       │   │   ├── data/
│       │   │   │   ├── local/
│       │   │   │   │   ├── database/
│       │   │   │   │   │   ├── AgyChatDatabase.kt
│       │   │   │   │   │   ├── Converters.kt
│       │   │   │   │   │   ├── dao/
│       │   │   │   │   │   │   ├── ChatSessionDao.kt
│       │   │   │   │   │   │   ├── ChatMessageDao.kt
│       │   │   │   │   │   │   ├── WorkspaceDao.kt
│       │   │   │   │   │   │   └── ArtifactCacheDao.kt
│       │   │   │   │   │   └── entity/
│       │   │   │   │   │       ├── ChatSessionEntity.kt
│       │   │   │   │   │       ├── ChatMessageEntity.kt
│       │   │   │   │   │       ├── WorkspaceEntity.kt
│       │   │   │   │   │       └── ArtifactCacheEntity.kt
│       │   │   │   │   ├── datastore/
│       │   │   │   │   │   ├── SettingsDataStore.kt
│       │   │   │   │   │   └── SettingsKeys.kt
│       │   │   │   │   └── mapper/
│       │   │   │   │       ├── ChatSessionEntityMapper.kt
│       │   │   │   │       ├── ChatMessageEntityMapper.kt
│       │   │   │   │       ├── WorkspaceEntityMapper.kt
│       │   │   │   │       └── ArtifactCacheEntityMapper.kt
│       │   │   │   │
│       │   │   │   ├── pty/                                (layer 1 — PTY Bridge)
│       │   │   │   │   ├── PtyBridgeService.kt
│       │   │   │   │   ├── PtyNativeBindings.kt
│       │   │   │   │   ├── PtyProcessSpawner.kt
│       │   │   │   │   ├── PtyWinsizeSyncer.kt
│       │   │   │   │   ├── PtySigwinchListener.kt
│       │   │   │   │   ├── PtyInputWriter.kt
│       │   │   │   │   ├── PtyOutputReader.kt
│       │   │   │   │   ├── PtyConnectionStateHolder.kt
│       │   │   │   │   └── PtyBridgeRepositoryImpl.kt
│       │   │   │   │
│       │   │   │   ├── terminal/                           (layer 2 — Terminal Emulation)
│       │   │   │   │   ├── TerminalEmulatorWrapper.kt
│       │   │   │   │   ├── TerminalScreenBuffer.kt
│       │   │   │   │   ├── TerminalIdleSettleDetector.kt
│       │   │   │   │   ├── TerminalSnapshotDiffer.kt
│       │   │   │   │   ├── TerminalLineDeltaEmitter.kt
│       │   │   │   │   └── TerminalEmulatorRepositoryImpl.kt
│       │   │   │   │
│       │   │   │   ├── parser/                             (layer 3 — Semantic Parser)
│       │   │   │   │   ├── EventParserEngine.kt
│       │   │   │   │   ├── pattern/
│       │   │   │   │   │   ├── ToolCallPattern.kt
│       │   │   │   │   │   ├── ThinkingBlockPattern.kt
│       │   │   │   │   │   ├── DiffPreviewPattern.kt
│       │   │   │   │   │   ├── PermissionPromptPattern.kt
│       │   │   │   │   │   ├── StatusLinePattern.kt
│       │   │   │   │   │   └── SessionResumeHintPattern.kt
│       │   │   │   │   ├── ToolCallParser.kt
│       │   │   │   │   ├── ThinkingBlockParser.kt
│       │   │   │   │   ├── DiffPreviewParser.kt
│       │   │   │   │   ├── PermissionPromptParser.kt
│       │   │   │   │   ├── StatusLineParser.kt
│       │   │   │   │   ├── SessionResumeHintParser.kt
│       │   │   │   │   └── PlainTextFallbackParser.kt
│       │   │   │   │
│       │   │   │   ├── actioninjector/                     (layer 6 — Action Injector)
│       │   │   │   │   ├── ArrowKeyEscapeSequenceBuilder.kt
│       │   │   │   │   ├── ArrowNavigationCalculator.kt
│       │   │   │   │   └── PermissionInjectorImpl.kt
│       │   │   │   │
│       │   │   │   ├── artifact/                           (layer 4 — Artifact Watcher)
│       │   │   │   │   ├── BrainFolderFileObserver.kt
│       │   │   │   │   ├── TaskMarkdownParser.kt
│       │   │   │   │   ├── PlanMarkdownParser.kt
│       │   │   │   │   ├── WalkthroughMarkdownParser.kt
│       │   │   │   │   ├── ArtifactMetadataJsonParser.kt
│       │   │   │   │   └── ArtifactRepositoryImpl.kt
│       │   │   │   │
│       │   │   │   └── repository/
│       │   │   │       ├── ChatSessionRepositoryImpl.kt
│       │   │   │       ├── ChatMessageRepositoryImpl.kt
│       │   │   │       ├── WorkspaceRepositoryImpl.kt
│       │   │   │       └── SettingsRepositoryImpl.kt
│       │   │   │
│       │   │   ├── presentation/                           (Compose)
│       │   │   │   ├── navigation/
│       │   │   │   │   ├── AgyChatNavHost.kt
│       │   │   │   │   ├── NavRoutes.kt
│       │   │   │   │   └── NavigationActions.kt
│       │   │   │   │
│       │   │   │   ├── theme/
│       │   │   │   │   ├── Color.kt
│       │   │   │   │   ├── Type.kt
│       │   │   │   │   ├── Shape.kt
│       │   │   │   │   ├── Theme.kt
│       │   │   │   │   └── Dimens.kt
│       │   │   │   │
│       │   │   │   ├── common/
│       │   │   │   │   ├── AgyChatTopBar.kt
│       │   │   │   │   ├── AgyChatButton.kt
│       │   │   │   │   ├── AgyChatTextField.kt
│       │   │   │   │   ├── LoadingIndicator.kt
│       │   │   │   │   ├── EmptyStateView.kt
│       │   │   │   │   ├── ErrorStateView.kt
│       │   │   │   │   ├── ConnectionStatusBadge.kt
│       │   │   │   │   └── CollapsibleCard.kt
│       │   │   │   │
│       │   │   │   ├── chatlist/
│       │   │   │   │   ├── ChatListScreen.kt
│       │   │   │   │   ├── ChatListViewModel.kt
│       │   │   │   │   ├── ChatListUiState.kt
│       │   │   │   │   ├── ChatListUiEvent.kt
│       │   │   │   │   ├── ChatListItem.kt
│       │   │   │   │   ├── NewChatFab.kt
│       │   │   │   │   └── ChatListItemActions.kt
│       │   │   │   │
│       │   │   │   ├── chat/
│       │   │   │   │   ├── ChatScreen.kt
│       │   │   │   │   ├── ChatViewModel.kt
│       │   │   │   │   ├── ChatUiState.kt
│       │   │   │   │   ├── ChatUiEvent.kt
│       │   │   │   │   ├── ChatInputBar.kt
│       │   │   │   │   ├── ChatMessageList.kt
│       │   │   │   │   ├── ChatStatusHeader.kt
│       │   │   │   │   └── bubble/
│       │   │   │   │       ├── UserMessageBubble.kt
│       │   │   │   │       ├── AssistantTextBubble.kt
│       │   │   │   │       ├── ToolCallCard.kt
│       │   │   │   │       ├── ThinkingCollapsible.kt
│       │   │   │   │       ├── DiffPreviewCard.kt
│       │   │   │   │       ├── DiffLineRow.kt
│       │   │   │   │       ├── PermissionPromptCard.kt
│       │   │   │   │       ├── PermissionOptionButton.kt
│       │   │   │   │       └── StatusLineChip.kt
│       │   │   │   │
│       │   │   │   ├── plan/
│       │   │   │   │   ├── PlanViewerScreen.kt
│       │   │   │   │   ├── PlanViewerViewModel.kt
│       │   │   │   │   ├── PlanViewerUiState.kt
│       │   │   │   │   ├── PlanViewerUiEvent.kt
│       │   │   │   │   ├── PlanCard.kt
│       │   │   │   │   ├── WalkthroughCard.kt
│       │   │   │   │   └── TaskSummaryCard.kt
│       │   │   │   │
│       │   │   │   ├── workspace/
│       │   │   │   │   ├── WorkspaceListScreen.kt
│       │   │   │   │   ├── WorkspaceListViewModel.kt
│       │   │   │   │   ├── WorkspaceListUiState.kt
│       │   │   │   │   ├── WorkspaceListUiEvent.kt
│       │   │   │   │   ├── WorkspaceItem.kt
│       │   │   │   │   └── CreateWorkspaceDialog.kt
│       │   │   │   │
│       │   │   │   └── settings/
│       │   │   │       ├── SettingsScreen.kt
│       │   │   │       ├── SettingsViewModel.kt
│       │   │   │       ├── SettingsUiState.kt
│       │   │   │       ├── SettingsUiEvent.kt
│       │   │   │       ├── AgyPathPickerField.kt
│       │   │   │       ├── IdleSettleDelaySlider.kt
│       │   │   │       └── ValidateInstallationSection.kt
│       │   │   │
│       │   │   ├── service/
│       │   │   │   ├── PtyForegroundService.kt
│       │   │   │   ├── ArtifactWatcherService.kt
│       │   │   │   └── ServiceNotificationBuilder.kt
│       │   │   │
│       │   │   ├── util/
│       │   │   │   ├── AnsiConstants.kt
│       │   │   │   ├── RegexPatterns.kt
│       │   │   │   ├── DispatcherProvider.kt
│       │   │   │   ├── Result.kt
│       │   │   │   ├── Extensions.kt
│       │   │   │   ├── DateTimeFormatter.kt
│       │   │   │   ├── UuidExtractor.kt
│       │   │   │   └── Logger.kt
│       │   │   │
│       │   │   └── permissionmapping/
│       │   │       ├── DefaultPermissionCursorAssumption.kt
│       │   │       └── PermissionOptionIndexTracker.kt
│       │   │
│       │   └── res/
│       │       ├── values/
│       │       │   ├── strings.xml
│       │       │   ├── strings_chat.xml
│       │       │   ├── strings_settings.xml
│       │       │   ├── strings_workspace.xml
│       │       │   ├── colors.xml
│       │       │   └── themes.xml
│       │       ├── drawable/
│       │       │   ├── ic_tool_call.xml
│       │       │   ├── ic_thinking.xml
│       │       │   ├── ic_diff.xml
│       │       │   ├── ic_permission.xml
│       │       │   ├── ic_connection_ok.xml
│       │       │   ├── ic_connection_lost.xml
│       │       │   └── ic_launcher_foreground.xml
│       │       └── mipmap/
│       │           └── (launcher icons)
│       │
│       └── test/
│           └── java/com/agychat/app/
│               ├── parser/
│               │   ├── ToolCallParserTest.kt
│               │   ├── ThinkingBlockParserTest.kt
│               │   ├── DiffPreviewParserTest.kt
│               │   ├── PermissionPromptParserTest.kt
│               │   └── StatusLineParserTest.kt
│               ├── terminal/
│               │   ├── TerminalSnapshotDifferTest.kt
│               │   └── TerminalIdleSettleDetectorTest.kt
│               ├── repository/
│               │   ├── PtyBridgeRepositoryImplTest.kt
│               │   └── ArtifactRepositoryImplTest.kt
│               └── usecase/
│                   ├── ComputeArrowNavigationDeltaUseCaseTest.kt
│                   └── SendPermissionResponseUseCaseTest.kt
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradle/libs.versions.toml
```

**~242-file project structure.** Generation will proceed folder-by-folder (domain/model → domain/repository → domain/usecase → data/pty → data/terminal → data/parser → data/artifact → data/repository → di → presentation, screen by screen) — same batching pattern as previous projects.
