package com.agychat.app.domain.usecase.session

import com.agychat.app.domain.model.ChatSession
import com.agychat.app.domain.repository.ChatSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all chat sessions.
 */
class GetChatSessionsUseCase @Inject constructor(
    private val repo: ChatSessionRepository
) {
    operator fun invoke(): Flow<List<ChatSession>> {
        return repo.getAll()
    }
}
