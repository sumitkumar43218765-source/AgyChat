package com.agychat.app.presentation.chat

sealed class ChatUiEvent {
    data class SendMessage(val text: String) : ChatUiEvent()
    data class RespondToPermission(val promptId: String, val selectedIndex: Int, val currentIndex: Int) : ChatUiEvent()
    data class ExpandToolCall(val messageId: String) : ChatUiEvent()
    data class ExpandDiff(val messageId: String) : ChatUiEvent()
    data class ExpandThinking(val messageId: String) : ChatUiEvent()
    object RetryConnection : ChatUiEvent()
    object StopAgent : ChatUiEvent()
}
