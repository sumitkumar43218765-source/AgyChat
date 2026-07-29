package com.agychat.app.data.terminal

import com.agychat.app.domain.model.TerminalLineDelta
import com.agychat.app.domain.model.TerminalScreenSnapshot
import com.agychat.app.domain.repository.TerminalEmulatorPort
import com.agychat.app.di.TerminalEmulatorWrapper
import javax.inject.Inject

class TerminalEmulatorPortImpl @Inject constructor(
    private val wrapper: TerminalEmulatorWrapper
) : TerminalEmulatorPort {
    override suspend fun feedBytes(bytes: ByteArray) {}
    override suspend fun getSnapshot(): TerminalScreenSnapshot {
        return TerminalScreenSnapshot(emptyList(), 0, 0, 0L)
    }
    override fun diffSnapshots(old: TerminalScreenSnapshot, new: TerminalScreenSnapshot): TerminalLineDelta {
        return TerminalLineDelta(emptyList(), 0, 0)
    }
    override suspend fun reset() {}
}
