package com.agychat.app.presentation.chatlist

import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.model.ProjectWorkspace

data class ChatListUiState(
    val sessions: List<ChatSession> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val activeWorkspace: ProjectWorkspace? = null
)
