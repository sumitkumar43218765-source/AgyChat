package com.agychat.app.domain.usecase.workspace

import com.agychat.app.domain.repository.WorkspaceRepository
import javax.inject.Inject

/**
 * Use case to select the active workspace.
 */
class SelectActiveWorkspaceUseCase @Inject constructor(
    private val repo: WorkspaceRepository
) {
    suspend operator fun invoke(id: String) {
        repo.setActive(id)
    }
}
