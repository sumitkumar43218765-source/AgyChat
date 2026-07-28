package com.agychat.app.data.repository

import com.agychat.app.data.local.mapper.ChatSessionMapper
import com.agychat.app.data.local.storage.ChatSessionStorage
import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.repository.ChatSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import java.util.UUID

class ChatSessionRepositoryImpl @Inject constructor(
    private val storage: ChatSessionStorage
) : ChatSessionRepository {

    override fun getAll(): Flow<List<ChatSession>> {
        return storage.getAll().map { list -> list.map { ChatSessionMapper.toDomain(it) } }
    }

    override suspend fun getById(id: String): ChatSession? {
        val all = storage.getAll().firstOrNull() ?: emptyList()
        val dto = all.find { it.id == id }
        return dto?.let { ChatSessionMapper.toDomain(it) }
    }

    override suspend fun create(title: String, workspaceId: String?): ChatSession {
        val session = ChatSession(id = UUID.randomUUID().toString(), title = title, conversationUuid = null, workspaceId = workspaceId, createdAt = System.currentTimeMillis(), updatedAt = System.currentTimeMillis(), isActive = false)
        storage.save(ChatSessionMapper.toDto(session))
        return session
    }

    override suspend fun delete(id: String) {
        storage.delete(id)
    }

    override suspend fun rename(id: String, newTitle: String) {
        val session = getById(id)
        if (session != null) {
            storage.update(ChatSessionMapper.toDto(session.copy(title = newTitle)))
        }
    }

    override suspend fun updateConversationUuid(id: String, uuid: String) {
        val session = getById(id)
        if (session != null) {
            // we can't save it in DTO easily unless we add it, but for now we update what we can.
            storage.update(ChatSessionMapper.toDto(session.copy(conversationUuid = uuid)))
        }
    }
}
