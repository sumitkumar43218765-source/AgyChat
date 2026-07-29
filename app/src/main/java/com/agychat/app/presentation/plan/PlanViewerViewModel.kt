package com.agychat.app.presentation.plan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.usecase.artifact.StartArtifactWatcherUseCase
import com.agychat.app.domain.usecase.artifact.StopArtifactWatcherUseCase
import com.agychat.app.domain.usecase.session.GetChatSessionByIdUseCase
import com.agychat.app.domain.repository.ArtifactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewerViewModel @Inject constructor(
    private val startArtifactWatcherUseCase: StartArtifactWatcherUseCase,
    private val stopArtifactWatcherUseCase: StopArtifactWatcherUseCase,
    private val getChatSessionByIdUseCase: GetChatSessionByIdUseCase,
    private val artifactRepo: ArtifactRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanViewerUiState())
    val uiState: StateFlow<PlanViewerUiState> = _uiState.asStateFlow()

    private var sessionId: String? = savedStateHandle.get<String>("sessionId")
    private var conversationUuid: String? = null

    init {
        loadSessionAndStartWatcher()
    }

    private fun loadSessionAndStartWatcher() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val id = sessionId
            if (id == null) {
                _uiState.update { it.copy(isLoading = false, error = "Session ID is missing") }
                return@launch
            }
            try {
                val session = getChatSessionByIdUseCase(id)
                if (session != null) {
                    conversationUuid = session.conversationUuid
                    if (session.conversationUuid != null) {
                        startWatcher(session.conversationUuid)
                    }
                    _uiState.update { it.copy(isLoading = false) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Session not found") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.localizedMessage ?: "Unknown error") }
            }
        }
    }

    private fun startWatcher(uuid: String) {
        viewModelScope.launch {
            try {
                startArtifactWatcherUseCase(uuid)
                launch {
                    artifactRepo.observePlanArtifact().collect { artifact ->
                        _uiState.update { it.copy(planArtifact = artifact) }
                    }
                }
                launch {
                    artifactRepo.observeWalkthroughArtifact().collect { artifact ->
                        _uiState.update { it.copy(walkthroughArtifact = artifact) }
                    }
                }
                launch {
                    artifactRepo.observeTaskArtifact().collect { artifact ->
                        _uiState.update { it.copy(taskArtifact = artifact) }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage) }
            }
        }
    }

    fun onEvent(event: PlanViewerUiEvent) {
        when (event) {
            is PlanViewerUiEvent.SelectTab -> {
                _uiState.update { it.copy(selectedTab = event.index) }
            }
            is PlanViewerUiEvent.RefreshArtifacts -> {
                loadSessionAndStartWatcher()
            }
            is PlanViewerUiEvent.DismissError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        kotlinx.coroutines.GlobalScope.launch(kotlinx.coroutines.NonCancellable) {
            try { stopArtifactWatcherUseCase() } catch (_: Exception) {}
        }
    }
}
