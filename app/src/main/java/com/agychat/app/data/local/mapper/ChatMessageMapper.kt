package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.ChatMessageDto
import com.agychat.app.domain.model.ChatMessage

internal object ChatMessageMapper {
    fun toDomain(dto: ChatMessageDto): ChatMessage {
        return ChatMessage(
            id = dto.id,
            sessionId = dto.sessionId,
            role = dto.role,
            content = dto.content,
            timestamp = dto.timestamp
        )
    }

    fun toDto(domain: ChatMessage): ChatMessageDto {
        return ChatMessageDto(
            id = domain.id,
            sessionId = domain.sessionId,
            role = domain.role,
            content = domain.content,
            timestamp = domain.timestamp
        )
    }
}
