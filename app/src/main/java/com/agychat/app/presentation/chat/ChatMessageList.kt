package com.agychat.app.presentation.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.MessageType
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.chat.bubble.*

@Composable
fun ChatMessageList(
    messages: List<ChatMessage>,
    modifier: Modifier = Modifier,
    onEvent: (ChatUiEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        reverseLayout = true,
        contentPadding = PaddingValues(Dimens.spacingM)
    ) {
        items(messages, key = { it.id }) { msg ->
            when (msg.type) {
                MessageType.USER -> UserMessageBubble(text = msg.text, timestamp = msg.timestamp)
                MessageType.ASSISTANT_TEXT -> msg.assistantContent?.let { AssistantTextBubble(it) }
                MessageType.TOOL_CALL -> msg.toolCallContent?.let { ToolCallCard(it, false, {}) }
                MessageType.THINKING_BLOCK -> msg.thinkingContent?.let { ThinkingCollapsible(it, false, {}) }
                MessageType.DIFF_PREVIEW -> msg.diffContent?.let { DiffPreviewCard(it, false, {}) }
                MessageType.PERMISSION_PROMPT -> msg.permissionContent?.let { PermissionPromptCard(it) { i -> onEvent(ChatUiEvent.RespondToPermission(msg.id, i, 0)) } }
                MessageType.STATUS_LINE -> msg.statusContent?.let { StatusLineChip(it) }
            }
        }
    }
}
