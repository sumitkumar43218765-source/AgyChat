package com.agychat.app.domain.usecase.terminal

import com.agychat.app.domain.model.TerminalScreenSnapshot
import com.agychat.app.domain.repository.TerminalEmulatorPort
import javax.inject.Inject

/**
 * Use case to take a snapshot of the stable screen.
 */
class SnapshotStableScreenUseCase @Inject constructor(
    private val terminalPort: TerminalEmulatorPort
) {
    suspend operator fun invoke(): TerminalScreenSnapshot {
        return terminalPort.getSnapshot()
    }
}
