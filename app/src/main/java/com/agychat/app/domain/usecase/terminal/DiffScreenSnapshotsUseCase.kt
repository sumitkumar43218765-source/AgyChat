package com.agychat.app.domain.usecase.terminal

import com.agychat.app.domain.model.TerminalLineDelta
import com.agychat.app.domain.model.TerminalScreenSnapshot
import com.agychat.app.domain.repository.TerminalEmulatorPort
import javax.inject.Inject

/**
 * Use case to diff screen snapshots.
 */
class DiffScreenSnapshotsUseCase @Inject constructor(
    private val terminalPort: TerminalEmulatorPort
) {
    operator fun invoke(old: TerminalScreenSnapshot, new: TerminalScreenSnapshot): TerminalLineDelta {
        return terminalPort.diffSnapshots(old, new)
    }
}
