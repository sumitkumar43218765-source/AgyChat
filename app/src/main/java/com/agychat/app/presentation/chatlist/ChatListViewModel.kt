package com.agychat.app.presentation.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.usecase.session.CreateChatSessionUseCase
import com.agychat.app.domain.usecase.session.DeleteChatSessionUseCase
import com.agychat.app.domain.usecase.session.GetChatSessionsUseCase
import com.agychat.app.domain.usecase.session.RenameChatSessionUseCase
import com.agychat.app.domain.usecase.workspace.GetWorkspacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatSessionsUseCase: GetChatSessionsUseCase,
    private val createChatSessionUseCase: CreateChatSessionUseCase,
    private val deleteChatSessionUseCase: DeleteChatSessionUseCase,
    private val renameChatSessionUseCase: RenameChatSessionUseCase,
    private val getWorkspacesUseCase: GetWorkspacesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        loadSessions()
    }

    private fun loadSessions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getChatSessionsUseCase().collect { sessions ->
                _uiState.value = _uiState.value.copy(sessions = sessions, isLoading = false)
            }
        }
    }

    fun onEvent(event: ChatListUiEvent) {
        when (event) {
            is ChatListUiEvent.CreateNewChat -> viewModelScope.launch { createChatSessionUseCase() }
            is ChatListUiEvent.DeleteChat -> viewModelScope.launch { deleteChatSessionUseCase(event.id) }
            is ChatListUiEvent.RenameChat -> viewModelScope.launch { renameChatSessionUseCase(event.id, event.newTitle) }
            is ChatListUiEvent.OpenChat -> { /* Handled via UI navigation */ }
            is ChatListUiEvent.OpenSettings -> { /* Handled via UI navigation */ }
            is ChatListUiEvent.OpenWorkspaces -> { /* Handled via UI navigation */ }
        }
    }
}
