package com.agychat.app.domain.usecase.workspace

import com.agychat.app.domain.model.ProjectWorkspace
import com.agychat.app.domain.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all workspaces.
 */
class GetWorkspacesUseCase @Inject constructor(
    private val repo: WorkspaceRepository
) {
    operator fun invoke(): Flow<List<ProjectWorkspace>> {
        return repo.getAll()
    }
}
