package com.agychat.app.presentation.terminal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.agychat.app.utils.AgyLogger
import com.termux.terminal.TerminalSession
import com.termux.terminal.TerminalSessionClient
import java.io.File

data class TerminalUiState(
    val isProcessRunning: Boolean = false,
    val session: TerminalSession? = null
)

sealed class TerminalUiEvent {
    data object ClearTerminal : TerminalUiEvent()
}

@HiltViewModel
class TerminalSheetViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TerminalUiState())
    val uiState: StateFlow<TerminalUiState> = _uiState.asStateFlow()

    private var terminalSession: TerminalSession? = null

    init {
        startTerminalSession()
    }

    fun onEvent(event: TerminalUiEvent) {
        when (event) {
            is TerminalUiEvent.ClearTerminal -> {
                startTerminalSession()
            }
        }
    }

    private fun startTerminalSession() {
        terminalSession?.finishIfRunning()
        
        val shellPath = "/system/bin/sh"
        val cwd = "/"
        val args = arrayOf("-l")
        val env = arrayOf(
            "PATH=/system/bin:/system/xbin",
            "TERM=xterm-256color"
        )
        
        val client = object : TerminalSessionClient {
            override fun onTextChanged(changedSession: TerminalSession) {}
            override fun onTitleChanged(changedSession: TerminalSession) {}
            override fun onSessionFinished(finishedSession: TerminalSession) {
                _uiState.update { it.copy(isProcessRunning = false) }
            }
            override fun onCopyTextToClipboard(session: TerminalSession, text: String) {}
            override fun onPasteTextFromClipboard(session: TerminalSession) {}
            override fun onBell(session: TerminalSession) {}
            override fun onColorsChanged(session: TerminalSession) {}
            override fun onTerminalCursorStateChange(state: Boolean) {}
            override fun getTerminalCursorStyle(): Int = 0
            override fun logError(tag: String, message: String) { AgyLogger.e(tag, message) }
            override fun logWarn(tag: String, message: String) { AgyLogger.w(tag, message) }
            override fun logInfo(tag: String, message: String) { AgyLogger.i(tag, message) }
            override fun logDebug(tag: String, message: String) { AgyLogger.d(tag, message) }
            override fun logVerbose(tag: String, message: String) { AgyLogger.v(tag, message) }
            override fun logStackTraceWithMessage(tag: String, message: String, e: Exception) { AgyLogger.e(tag, message, e) }
            override fun logStackTrace(tag: String, e: Exception) { AgyLogger.e(tag, "Exception", e) }
        }

        try {
            terminalSession = TerminalSession(shellPath, cwd, args, env, 1000, client)
            _uiState.update { 
                it.copy(
                    session = terminalSession,
                    isProcessRunning = true
                ) 
            }
        } catch (e: Exception) {
            AgyLogger.e("TerminalSheetViewModel", "Failed to start terminal session", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        terminalSession?.finishIfRunning()
    }
}
