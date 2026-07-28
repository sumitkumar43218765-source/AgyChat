package com.agychat.app.domain.model

/**
 * Represents an event parsed from an agent's response stream.
 */
sealed class ParsedEvent {
    data class AssistantText(val content: AssistantTextContent) : ParsedEvent()
    data class ToolCall(val content: ToolCallContent) : ParsedEvent()
    data class ThinkingBlock(val content: ThinkingBlockContent) : ParsedEvent()
    data class DiffPreview(val content: DiffPreviewContent) : ParsedEvent()
    data class PermissionPrompt(val content: PermissionPromptContent) : ParsedEvent()
    data class StatusLine(val content: StatusLineContent) : ParsedEvent()
    data class SessionResumeHint(val info: SessionResumeInfo) : ParsedEvent()
    data class Unknown(val rawText: String) : ParsedEvent()
}
