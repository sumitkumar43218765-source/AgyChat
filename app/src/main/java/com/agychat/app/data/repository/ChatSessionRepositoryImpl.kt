package com.agychat.app.data.repository

import com.agychat.app.data.local.mapper.ChatSessionMapper
import com.agychat.app.data.local.storage.ChatSessionStorage
import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.repository.ChatSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatSessionRepositoryImpl @Inject constructor(
    private val storage: ChatSessionStorage
) : ChatSessionRepository {

    override fun getAllSessions(): Flow<List<ChatSession>> {
        return storage.getAll().map { list -> list.map { ChatSessionMapper.toDomain(it) } }
    }

    override suspend fun saveSession(session: ChatSession) {
        storage.save(ChatSessionMapper.toDto(session))
    }

    override suspend fun updateSession(session: ChatSession) {
        storage.update(ChatSessionMapper.toDto(session))
    }

    override suspend fun deleteSession(id: String) {
        storage.delete(id)
    }
}
