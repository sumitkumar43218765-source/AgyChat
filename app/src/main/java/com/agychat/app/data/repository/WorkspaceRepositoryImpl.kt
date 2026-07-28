package com.agychat.app.data.repository

import com.agychat.app.data.local.mapper.WorkspaceMapper
import com.agychat.app.data.local.storage.WorkspaceStorage
import com.agychat.app.domain.model.ProjectWorkspace
import com.agychat.app.domain.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkspaceRepositoryImpl @Inject constructor(
    private val storage: WorkspaceStorage
) : WorkspaceRepository {

    override fun getAllWorkspaces(): Flow<List<ProjectWorkspace>> {
        return storage.getAll().map { list -> list.map { WorkspaceMapper.toDomain(it) } }
    }

    override suspend fun saveWorkspace(workspace: ProjectWorkspace) {
        storage.save(WorkspaceMapper.toDto(workspace))
    }

    override suspend fun deleteWorkspace(id: String) {
        storage.delete(id)
    }

    override suspend fun setActiveWorkspace(id: String) {
        storage.setActive(id)
    }
}
