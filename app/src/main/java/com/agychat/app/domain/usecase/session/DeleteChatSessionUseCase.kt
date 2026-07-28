package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.repository.ChatSessionRepository
import javax.inject.Inject

/**
 * Use case to delete a chat session.
 */
class DeleteChatSessionUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    suspend operator fun invoke(id: String) {
        repo.delete(id)
    }
}
