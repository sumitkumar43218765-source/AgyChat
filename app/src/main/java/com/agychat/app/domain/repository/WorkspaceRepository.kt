package com.agychat.app.domain.repository

import com.agychat.app.domain.model.ProjectWorkspace
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing workspaces.
 */
interface WorkspaceRepository {
    fun getAll(): Flow<List<ProjectWorkspace>>
    suspend fun create(name: String, path: String): ProjectWorkspace
    suspend fun delete(id: String)
    suspend fun setActive(id: String)
    suspend fun getActive(): ProjectWorkspace?
}
