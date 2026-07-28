package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.repository.ChatSessionRepository
import javax.inject.Inject

/**
 * Use case to rename a chat session.
 */
class RenameChatSessionUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    suspend operator fun invoke(id: String, newTitle: String) {
        repo.rename(id, newTitle)
    }
}
