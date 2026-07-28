package com.agychat.app.data.repository

import com.agychat.app.data.local.mapper.ChatMessageMapper
import com.agychat.app.data.local.storage.ChatMessageStorage
import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.repository.ChatMessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatMessageRepositoryImpl @Inject constructor(
    private val storage: ChatMessageStorage
) : ChatMessageRepository {

    override fun observeMessages(sessionId: String): Flow<List<ChatMessage>> {
        return storage.getForSession(sessionId).map { list -> list.map { ChatMessageMapper.toDomain(it) } }
    }

    override suspend fun addMessage(message: ChatMessage) {
        storage.add(message.sessionId, ChatMessageMapper.toDto(message))
    }

    override suspend fun updateMessage(message: ChatMessage) {
        storage.update(message.sessionId, ChatMessageMapper.toDto(message))
    }

    override suspend fun clearHistory(sessionId: String) {
        storage.clearSession(sessionId)
    }

    override suspend fun getLastMessage(sessionId: String): ChatMessage? {
        return storage.getLastForSession(sessionId)?.let { ChatMessageMapper.toDomain(it) }
    }
}
