package com.agychat.app.presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.usecase.artifact.*
import com.agychat.app.domain.usecase.message.*
import com.agychat.app.domain.usecase.parser.*
import com.agychat.app.domain.usecase.permission.*
import com.agychat.app.domain.usecase.pty.*
import com.agychat.app.domain.usecase.session.*
import com.agychat.app.domain.usecase.terminal.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val observeChatMessagesUseCase: ObserveChatMessagesUseCase,
    private val sendUserMessageUseCase: SendUserMessageUseCase,
    private val appendParsedEventAsMessageUseCase: AppendParsedEventAsMessageUseCase,
    private val startAgyProcessUseCase: StartAgyProcessUseCase,
    private val stopAgyProcessUseCase: StopAgyProcessUseCase,
    private val writeRawInputToPtyUseCase: WriteRawInputToPtyUseCase,
    private val observePtyOutputUseCase: ObservePtyOutputUseCase,
    private val observePtyConnectionStateUseCase: ObservePtyConnectionStateUseCase,
    private val feedBytesToEmulatorUseCase: FeedBytesToEmulatorUseCase,
    private val snapshotStableScreenUseCase: SnapshotStableScreenUseCase,
    private val diffScreenSnapshotsUseCase: DiffScreenSnapshotsUseCase,
    private val parseLineDeltaUseCase: ParseLineDeltaUseCase,
    private val sendPermissionResponseUseCase: SendPermissionResponseUseCase,
    private val getChatSessionByIdUseCase: GetChatSessionByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val sessionId: String = savedStateHandle.get<String>("sessionId") ?: ""

    init {
        loadSession()
        observeMessages()
        observePty()
    }

    private fun loadSession() {
        viewModelScope.launch {
            val session = getChatSessionByIdUseCase(sessionId)
            _uiState.value = _uiState.value.copy(sessionTitle = session?.title ?: "")
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            observeChatMessagesUseCase(sessionId).collect { msgs ->
                _uiState.value = _uiState.value.copy(messages = msgs)
            }
        }
    }

    private fun observePty() {
        viewModelScope.launch {
            observePtyConnectionStateUseCase().collect { state ->
                _uiState.value = _uiState.value.copy(connectionState = state)
            }
        }
    }

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendMessage -> viewModelScope.launch { sendUserMessageUseCase(sessionId, event.text) }
            is ChatUiEvent.RespondToPermission -> viewModelScope.launch { sendPermissionResponseUseCase(event.promptId, event.selectedIndex) }
            is ChatUiEvent.StopAgent -> viewModelScope.launch { stopAgyProcessUseCase() }
            // Expansion toggles would update message state locally or in domain
            else -> {}
        }
    }
}
