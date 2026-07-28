package com.agychat.app.domain.usecase.message

import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.ChatMessageType
import com.agychat.app.domain.repository.ChatMessageRepository
import javax.inject.Inject
import java.util.UUID

/**
 * Use case to send a user message.
 */
class SendUserMessageUseCase @Inject constructor(
    private val msgRepo: ChatMessageRepository
) {
    suspend operator fun invoke(sessionId: String, text: String): ChatMessage {
        val msg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = sessionId,
            text = text,
            type = ChatMessageType.USER,
            timestamp = System.currentTimeMillis()
        )
        msgRepo.addMessage(msg)
        return msg
    }
}
