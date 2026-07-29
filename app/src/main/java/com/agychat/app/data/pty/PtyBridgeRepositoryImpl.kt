package com.agychat.app.data.pty

import com.agychat.app.domain.model.PtyConnectionState
import com.agychat.app.domain.model.PtySize
import com.agychat.app.domain.repository.PtyBridgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PtyBridgeRepositoryImpl @Inject constructor(
    private val service: PtyBridgeService
) : PtyBridgeRepository {
    
    override suspend fun startProcess(command: String, args: List<String>, cwd: String, size: PtySize): Boolean {
        return service.startProcess(command, args, cwd, size)
    }

    override suspend fun stopProcess() {
        service.stopProcess()
    }

    override suspend fun writeInput(bytes: ByteArray) {
        service.writeInput(bytes)
    }

    override fun observeOutput(): Flow<ByteArray> {
        return service.observeOutput()
    }

    override fun observeConnectionState(): Flow<PtyConnectionState> {
        return service.observeConnectionState()
    }

    override suspend fun syncWindowSize(size: PtySize) {
        service.syncWindowSize(size)
    }

    override fun isRunning(): Boolean {
        return service.isRunning()
    }
}
