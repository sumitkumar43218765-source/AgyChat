package com.agychat.app.data.local.mapper

import com.agychat.app.data.local.storage.WorkspaceDto
import com.agychat.app.domain.model.ProjectWorkspace

internal object WorkspaceMapper {
    fun toDomain(dto: WorkspaceDto): ProjectWorkspace {
        return ProjectWorkspace(
            id = dto.id,
            name = dto.name,
            path = dto.uri,
            isActive = dto.isActive,
            createdAt = 0L
        )
    }

    fun toDto(domain: ProjectWorkspace): WorkspaceDto {
        return WorkspaceDto(
            id = domain.id,
            name = domain.name,
            uri = domain.path,
            isActive = domain.isActive
        )
    }
}
