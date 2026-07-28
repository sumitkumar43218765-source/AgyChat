package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.ChatSessionDto
import com.agychat.app.domain.model.ChatSession

object ChatSessionMapper {
    fun toDomain(dto: ChatSessionDto): ChatSession {
        return ChatSession(
            id = dto.id,
            title = dto.title,
            conversationUuid = null,
            workspaceId = dto.workspaceId,
            createdAt = dto.timestamp,
            updatedAt = dto.timestamp,
            isActive = false
        )
    }

    fun toDto(domain: ChatSession): ChatSessionDto {
        return ChatSessionDto(
            id = domain.id,
            title = domain.title,
            timestamp = domain.createdAt,
            workspaceId = domain.workspaceId
        )
    }
}
