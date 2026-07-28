package com.agychat.app.domain.repository

import com.agychat.app.domain.model.TerminalLineDelta
import com.agychat.app.domain.model.TerminalScreenSnapshot

/**
 * Port interface for terminal emulator operations.
 */
interface TerminalEmulatorPort {
    suspend fun feedBytes(bytes: ByteArray)
    suspend fun getSnapshot(): TerminalScreenSnapshot
    fun diffSnapshots(old: TerminalScreenSnapshot, new: TerminalScreenSnapshot): TerminalLineDelta
    suspend fun reset()
}
