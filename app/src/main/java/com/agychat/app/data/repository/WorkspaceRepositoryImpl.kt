package com.agychat.app.data.repository

import com.agychat.app.data.local.mapper.WorkspaceMapper
import com.agychat.app.data.local.storage.WorkspaceStorage
import com.agychat.app.domain.model.ProjectWorkspace
import com.agychat.app.domain.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class WorkspaceRepositoryImpl @Inject constructor(
    private val storage: WorkspaceStorage
) : WorkspaceRepository {

    override fun getAll(): Flow<List<ProjectWorkspace>> {
        return storage.getAll().map { list -> list.map { WorkspaceMapper.toDomain(it) } }
    }

    override suspend fun create(name: String, path: String): ProjectWorkspace {
        val workspace = ProjectWorkspace(id = java.util.UUID.randomUUID().toString(), name = name, path = path, isActive = false, createdAt = System.currentTimeMillis())
        storage.save(WorkspaceMapper.toDto(workspace))
        return workspace
    }

    override suspend fun delete(id: String) {
        storage.delete(id)
    }

    override suspend fun setActive(id: String) {
        storage.setActive(id)
    }

    override suspend fun getActive(): ProjectWorkspace? {
        val all = storage.getAll().firstOrNull() ?: emptyList()
        val activeDto = all.find { it.isActive }
        return activeDto?.let { WorkspaceMapper.toDomain(it) }
    }
}
