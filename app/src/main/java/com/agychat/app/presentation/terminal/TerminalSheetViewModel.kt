package com.agychat.app.presentation.terminal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TerminalUiState(
    val outputLines: List<String> = listOf(
        "$ Welcome to AgyChat Terminal",
        "$ Type commands below..."
    ),
    val currentInput: String = "",
    val isProcessRunning: Boolean = false,
    val scrollToBottom: Boolean = false
)

sealed class TerminalUiEvent {
    data class InputChanged(val text: String) : TerminalUiEvent()
    data object ExecuteCommand : TerminalUiEvent()
    data object ClearTerminal : TerminalUiEvent()
}

@HiltViewModel
class TerminalSheetViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TerminalUiState())
    val uiState: StateFlow<TerminalUiState> = _uiState.asStateFlow()

    private var process: Process? = null

    fun onEvent(event: TerminalUiEvent) {
        when (event) {
            is TerminalUiEvent.InputChanged -> {
                _uiState.update { it.copy(currentInput = event.text) }
            }
            is TerminalUiEvent.ExecuteCommand -> executeCommand()
            is TerminalUiEvent.ClearTerminal -> {
                _uiState.update {
                    it.copy(
                        outputLines = listOf("$ Terminal cleared."),
                        scrollToBottom = true
                    )
                }
            }
        }
    }

    private fun executeCommand() {
        val cmd = _uiState.value.currentInput.trim()
        if (cmd.isEmpty()) return

        _uiState.update {
            it.copy(
                outputLines = it.outputLines + "$ $cmd",
                currentInput = "",
                isProcessRunning = true,
                scrollToBottom = true
            )
        }

        viewModelScope.launch {
            try {
                val pb = ProcessBuilder("/bin/sh", "-c", cmd)
                pb.redirectErrorStream(true)
                val proc = pb.start()
                process = proc

                val reader = proc.inputStream.bufferedReader()
                val output = StringBuilder()
                var line = reader.readLine()
                while (line != null) {
                    output.appendLine(line)
                    _uiState.update {
                        it.copy(
                            outputLines = it.outputLines + line,
                            scrollToBottom = true
                        )
                    }
                    line = reader.readLine()
                }

                val exitCode = proc.waitFor()
                if (exitCode != 0) {
                    _uiState.update {
                        it.copy(
                            outputLines = it.outputLines + "[exit code: $exitCode]",
                            scrollToBottom = true
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        outputLines = it.outputLines + "[error: ${e.message}]",
                        scrollToBottom = true
                    )
                }
            } finally {
                _uiState.update { it.copy(isProcessRunning = false) }
                process = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        process?.destroyForcibly()
    }
}
