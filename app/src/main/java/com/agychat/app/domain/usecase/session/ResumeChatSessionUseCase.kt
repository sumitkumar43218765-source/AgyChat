package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.repository.ChatSessionRepository
import javax.inject.Inject

/**
 * Use case to resume a chat session by updating its conversation UUID.
 */
class ResumeChatSessionUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    suspend operator fun invoke(id: String, uuid: String) {
        repo.updateConversationUuid(id, uuid)
    }
}
