package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.ArtifactCacheDto
import com.agychat.app.domain.model.ArtifactCache

object ArtifactCacheMapper {
    fun toDomain(dto: ArtifactCacheDto): ArtifactCache {
        return ArtifactCache(
            conversationUuid = dto.conversationUuid,
            content = dto.content,
            lastUpdated = dto.lastUpdated
        )
    }

    fun toDto(domain: ArtifactCache): ArtifactCacheDto {
        return ArtifactCacheDto(
            conversationUuid = domain.conversationUuid,
            content = domain.content,
            lastUpdated = domain.lastUpdated
        )
    }
}
