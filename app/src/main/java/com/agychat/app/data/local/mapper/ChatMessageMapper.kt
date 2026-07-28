package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.ChatMessageDto
import com.agychat.app.domain.model.ChatMessage
import com.agychat.app.domain.model.MessageType

object ChatMessageMapper {
    fun toDomain(dto: ChatMessageDto): ChatMessage {
        return ChatMessage(
            id = dto.id,
            sessionId = dto.sessionId,
            type = try { MessageType.valueOf(dto.role) } catch (e: Exception) { MessageType.USER },
            content = dto.content,
            timestamp = dto.timestamp
        )
    }

    fun toDto(domain: ChatMessage): ChatMessageDto {
        return ChatMessageDto(
            id = domain.id,
            sessionId = domain.sessionId,
            role = domain.type.name,
            content = domain.content,
            timestamp = domain.timestamp
        )
    }
}
