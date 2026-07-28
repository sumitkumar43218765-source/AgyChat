package com.agychat.app.domain.repository

import com.agychat.app.domain.model.ChatSession
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing chat sessions.
 */
interface ChatSessionRepository {
    fun getAll(): Flow<List<ChatSession>>
    suspend fun getById(id: String): ChatSession?
    suspend fun create(title: String, workspaceId: String?): ChatSession
    suspend fun delete(id: String)
    suspend fun rename(id: String, newTitle: String)
    suspend fun updateConversationUuid(id: String, uuid: String)
}
