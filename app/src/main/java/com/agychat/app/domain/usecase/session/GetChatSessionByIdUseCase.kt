package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.repository.ChatSessionRepository
import javax.inject.Inject

/**
 * Use case to get a chat session by its ID.
 */
class GetChatSessionByIdUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    suspend operator fun invoke(id: String): ChatSession? {
        return repo.getById(id)
    }
}
