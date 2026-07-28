package com.agychat.app.domain.repository

import com.agychat.app.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing chat messages.
 */
interface ChatMessageRepository {
    fun observeMessages(sessionId: String): Flow<List<ChatMessage>>
    suspend fun addMessage(message: ChatMessage)
    suspend fun updateMessage(message: ChatMessage)
    suspend fun clearHistory(sessionId: String)
    suspend fun getLastMessage(sessionId: String): ChatMessage?
}
