package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.repository.ChatSessionRepository
import javax.inject.Inject

/**
 * Use case to create a new chat session.
 */
class CreateChatSessionUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    suspend operator fun invoke(title: String, workspaceId: String?): ChatSession {
        return repo.create(title, workspaceId)
    }
}
