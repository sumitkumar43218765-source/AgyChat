package com.agychat.app.domain.usecase.message

import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.repository.ChatMessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to observe chat messages for a session.
 */
class ObserveChatMessagesUseCase @Inject constructor(
    private val repo: ChatMessageRepository
) {
    operator fun invoke(sessionId: String): Flow<List<ChatMessage>> {
        return repo.observeMessages(sessionId)
    }
}
