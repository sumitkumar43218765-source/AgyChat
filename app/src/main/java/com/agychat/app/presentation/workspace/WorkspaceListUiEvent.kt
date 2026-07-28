package com.agychat.app.presentation.workspace

sealed class WorkspaceListUiEvent {
    object ShowCreateDialog : WorkspaceListUiEvent()
    object DismissCreateDialog : WorkspaceListUiEvent()
    data class CreateWorkspace(val name: String, val path: String) : WorkspaceListUiEvent()
    data class DeleteWorkspace(val id: String) : WorkspaceListUiEvent()
    data class SelectWorkspace(val id: String) : WorkspaceListUiEvent()
}
