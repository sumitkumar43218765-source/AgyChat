package com.agychat.app.domain.usecase.terminal

import com.agychat.app.domain.repository.TerminalEmulatorPort
import javax.inject.Inject

/**
 * Use case to feed bytes to the terminal emulator.
 */
class FeedBytesToEmulatorUseCase @Inject constructor(
    private val terminalPort: TerminalEmulatorPort
) {
    suspend operator fun invoke(bytes: ByteArray) {
        terminalPort.feedBytes(bytes)
    }
}
