package com.agychat.app.presentation.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.usecase.CreateWorkspaceUseCase
import com.agychat.app.domain.usecase.DeleteWorkspaceUseCase
import com.agychat.app.domain.usecase.GetWorkspacesUseCase
import com.agychat.app.domain.usecase.SelectActiveWorkspaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkspaceListViewModel @Inject constructor(
    private val getWorkspacesUseCase: GetWorkspacesUseCase,
    private val createWorkspaceUseCase: CreateWorkspaceUseCase,
    private val deleteWorkspaceUseCase: DeleteWorkspaceUseCase,
    private val selectActiveWorkspaceUseCase: SelectActiveWorkspaceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkspaceListUiState())
    val uiState: StateFlow<WorkspaceListUiState> = _uiState.asStateFlow()

    init {
        loadWorkspaces()
    }

    private fun loadWorkspaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getWorkspacesUseCase().collect { workspaces ->
                    _uiState.update { it.copy(workspaces = workspaces, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage) }
            }
        }
    }

    fun onEvent(event: WorkspaceListUiEvent) {
        when (event) {
            is WorkspaceListUiEvent.ShowCreateDialog -> {
                _uiState.update { it.copy(showCreateDialog = true) }
            }
            is WorkspaceListUiEvent.DismissCreateDialog -> {
                _uiState.update { it.copy(showCreateDialog = false) }
            }
            is WorkspaceListUiEvent.CreateWorkspace -> {
                viewModelScope.launch {
                    try {
                        createWorkspaceUseCase(event.name, event.path)
                        _uiState.update { it.copy(showCreateDialog = false) }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.localizedMessage) }
                    }
                }
            }
            is WorkspaceListUiEvent.DeleteWorkspace -> {
                viewModelScope.launch {
                    try {
                        deleteWorkspaceUseCase(event.id)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.localizedMessage) }
                    }
                }
            }
            is WorkspaceListUiEvent.SelectWorkspace -> {
                viewModelScope.launch {
                    try {
                        selectActiveWorkspaceUseCase(event.id)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.localizedMessage) }
                    }
                }
            }
        }
    }
}
