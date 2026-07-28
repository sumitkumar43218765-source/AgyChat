package com.agychat.app.domain.usecase.message

import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.ChatMessageType
import com.agychat.app.domain.model.ParsedEvent
import com.agychat.app.domain.repository.ChatMessageRepository
import javax.inject.Inject
import java.util.UUID

/**
 * Use case to append a parsed event as a chat message.
 */
class AppendParsedEventAsMessageUseCase @Inject constructor(
    private val repo: ChatMessageRepository
) {
    suspend operator fun invoke(sessionId: String, event: ParsedEvent): ChatMessage {
        val msg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = sessionId,
            text = event.toString(), // or map appropriately
            type = ChatMessageType.ASSISTANT,
            timestamp = System.currentTimeMillis(),
            parsedEvent = event
        )
        repo.addMessage(msg)
        return msg
    }
}
