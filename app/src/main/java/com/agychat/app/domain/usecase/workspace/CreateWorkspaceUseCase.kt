package com.agychat.app.domain.usecase.workspace

import com.agychat.app.domain.model.ProjectWorkspace
import com.agychat.app.domain.repository.WorkspaceRepository
import javax.inject.Inject

/**
 * Use case to create a new workspace.
 */
class CreateWorkspaceUseCase @Inject constructor(
    private val repo: WorkspaceRepository
) {
    suspend operator fun invoke(name: String, path: String): ProjectWorkspace {
        return repo.create(name, path)
    }
}
