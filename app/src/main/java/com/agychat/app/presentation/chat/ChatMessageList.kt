package com.agychat.app.presentation.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.*
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.chat.bubble.*
import com.google.gson.Gson
import androidx.compose.runtime.remember

@Composable
fun ChatMessageList(
    messages: List<ChatMessage>,
    modifier: Modifier = Modifier,
    onEvent: (ChatUiEvent) -> Unit
) {
    val gson = remember { Gson() }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        reverseLayout = true,
        contentPadding = PaddingValues(Dimens.SpacingMd)
    ) {
        items(messages, key = { it.id }) { msg ->
            when (msg.type) {
                MessageType.USER -> UserMessageBubble(text = msg.content, timestamp = msg.timestamp)
                MessageType.ASSISTANT_TEXT -> {
                    val content = try { gson.fromJson(msg.content, AssistantTextContent::class.java) } catch(e:Exception) { null }
                    content?.let { AssistantTextBubble(it) }
                }
                MessageType.TOOL_CALL -> {
                    val content = try { gson.fromJson(msg.content, ToolCallContent::class.java) } catch(e:Exception) { null }
                    content?.let { ToolCallCard(it, false, {}) }
                }
                MessageType.THINKING_BLOCK -> {
                    val content = try { gson.fromJson(msg.content, ThinkingBlockContent::class.java) } catch(e:Exception) { null }
                    content?.let { ThinkingCollapsible(it, false, {}) }
                }
                MessageType.DIFF_PREVIEW -> {
                    val content = try { gson.fromJson(msg.content, DiffPreviewContent::class.java) } catch(e:Exception) { null }
                    content?.let { DiffPreviewCard(it, false, {}) }
                }
                MessageType.PERMISSION_PROMPT -> {
                    val content = try { gson.fromJson(msg.content, PermissionPromptContent::class.java) } catch(e:Exception) { null }
                    content?.let { PermissionPromptCard(it) { i -> onEvent(ChatUiEvent.RespondToPermission(msg.id, i, 0)) } }
                }
                MessageType.STATUS_LINE -> {
                    val content = try { gson.fromJson(msg.content, StatusLineContent::class.java) } catch(e:Exception) { null }
                    content?.let { StatusLineChip(it) }
                }
                MessageType.PLAN_ARTIFACT,
                MessageType.WALKTHROUGH_ARTIFACT,
                MessageType.TASK_ARTIFACT -> {
                    // Optional rendering
                }
            }
        }
    }
}
