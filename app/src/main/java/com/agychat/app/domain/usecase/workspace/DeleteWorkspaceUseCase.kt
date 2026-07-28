package com.agychat.app.domain.usecase.workspace

import com.agychat.app.domain.repository.WorkspaceRepository
import javax.inject.Inject

/**
 * Use case to delete a workspace.
 */
class DeleteWorkspaceUseCase @Inject constructor(
    private val repo: WorkspaceRepository
) {
    suspend operator fun invoke(id: String) {
        repo.delete(id)
    }
}
