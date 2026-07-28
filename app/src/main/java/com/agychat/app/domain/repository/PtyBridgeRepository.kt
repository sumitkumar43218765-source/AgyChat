package com.agychat.app.domain.repository

import com.agychat.app.domain.model.PtyConnectionState
import com.agychat.app.domain.model.PtySize
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for PTY bridge operations.
 */
interface PtyBridgeRepository {
    suspend fun startProcess(command: String, args: List<String>, cwd: String, size: PtySize): Boolean
    suspend fun stopProcess()
    suspend fun writeInput(bytes: ByteArray)
    fun observeOutput(): Flow<ByteArray>
    fun observeConnectionState(): Flow<PtyConnectionState>
    suspend fun syncWindowSize(size: PtySize)
    fun isRunning(): Boolean
}
