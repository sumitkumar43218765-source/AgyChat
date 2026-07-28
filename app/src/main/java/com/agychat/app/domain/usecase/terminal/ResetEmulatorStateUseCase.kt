package com.agychat.app.domain.usecase.terminal

import com.agychat.app.domain.repository.TerminalEmulatorPort
import javax.inject.Inject

/**
 * Use case to reset the terminal emulator state.
 */
class ResetEmulatorStateUseCase @Inject constructor(
    private val terminalPort: TerminalEmulatorPort
) {
    suspend operator fun invoke() {
        terminalPort.reset()
    }
}
