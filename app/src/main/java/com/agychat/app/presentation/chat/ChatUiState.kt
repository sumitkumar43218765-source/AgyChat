package com.agychat.app.presentation.chat

import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.PtyConnectionState
import com.agychat.app.domain.model.StatusLineContent

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val isAgentTyping: Boolean = false,
    val connectionState: PtyConnectionState = PtyConnectionState.DISCONNECTED,
    val currentModel: StatusLineContent? = null,
    val sessionTitle: String = "",
    val error: String? = null
)
