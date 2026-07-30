package com.agychat.app.presentation.chatlist

import androidx.lifecycle.ViewModel
import com.agychat.app.domain.model.ChatSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor() : ViewModel() {
    
    private val _sessions = MutableStateFlow<List<ChatSession>>(emptyList())
    val sessions: StateFlow<List<ChatSession>> = _sessions.asStateFlow()

    init {
        loadSessions()
    }

    private fun loadSessions() {
        val dummyData = listOf(
            ChatSession(
                id = UUID.randomUUID().toString(),
                title = "Compose Layout Help",
                conversationUuid = null,
                workspaceId = null,
                createdAt = System.currentTimeMillis() - 100000,
                updatedAt = System.currentTimeMillis() - 50000,
                isActive = false
            ),
            ChatSession(
                id = UUID.randomUUID().toString(),
                title = "Terminal Setup Issue",
                conversationUuid = null,
                workspaceId = null,
                createdAt = System.currentTimeMillis() - 300000,
                updatedAt = System.currentTimeMillis() - 200000,
                isActive = false
            )
        )
        _sessions.value = dummyData
    }
}
