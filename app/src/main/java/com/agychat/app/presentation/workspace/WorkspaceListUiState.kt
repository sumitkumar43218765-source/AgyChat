package com.agychat.app.presentation.workspace

import com.agychat.app.domain.model.ProjectWorkspace

data class WorkspaceListUiState(
    val workspaces: List<ProjectWorkspace> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showCreateDialog: Boolean = false
)
