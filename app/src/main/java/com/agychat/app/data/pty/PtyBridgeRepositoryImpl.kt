package com.agychat.app.data.pty

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PtyBridgeRepository {
    fun startProcess(command: String, args: List<String>, cwd: String, size: PtySize): Boolean
    fun stopProcess()
    fun writeInput(bytes: ByteArray)
    fun observeOutput(): Flow<ByteArray>
    fun observeConnectionState(): Flow<PtyConnectionState>
    fun syncWindowSize(size: PtySize)
    fun isRunning(): Boolean
}

class PtyBridgeRepositoryImpl @Inject constructor(
    private val service: PtyBridgeService
) : PtyBridgeRepository {
    
    override fun startProcess(command: String, args: List<String>, cwd: String, size: PtySize): Boolean {
        return service.startProcess(command, args, cwd, size)
    }

    override fun stopProcess() {
        service.stopProcess()
    }

    override fun writeInput(bytes: ByteArray) {
        service.writeInput(bytes)
    }

    override fun observeOutput(): Flow<ByteArray> {
        return service.observeOutput()
    }

    override fun observeConnectionState(): Flow<PtyConnectionState> {
        return service.observeConnectionState()
    }

    override fun syncWindowSize(size: PtySize) {
        service.syncWindowSize(size)
    }

    override fun isRunning(): Boolean {
        return service.isRunning()
    }
}
