package com.agychat.app.domain.usecase.message

import com.agychat.app.domain.repository.ChatMessageRepository
import javax.inject.Inject

/**
 * Use case to clear chat history for a session.
 */
class ClearChatHistoryUseCase @Inject constructor(
    private val repo: ChatMessageRepository
) {
    suspend operator fun invoke(sessionId: String) {
        repo.clearHistory(sessionId)
    }
}
