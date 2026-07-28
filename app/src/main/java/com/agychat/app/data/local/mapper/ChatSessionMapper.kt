package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.ChatSessionDto
import com.agychat.app.domain.model.ChatSession

internal object ChatSessionMapper {
    fun toDomain(dto: ChatSessionDto): ChatSession {
        return ChatSession(
            id = dto.id,
            title = dto.title,
            timestamp = dto.timestamp,
            workspaceId = dto.workspaceId
        )
    }

    fun toDto(domain: ChatSession): ChatSessionDto {
        return ChatSessionDto(
            id = domain.id,
            title = domain.title,
            timestamp = domain.timestamp,
            workspaceId = domain.workspaceId
        )
    }
}
