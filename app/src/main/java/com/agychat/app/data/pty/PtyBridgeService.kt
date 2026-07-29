package com.agychat.app.data.pty

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

import com.agychat.app.domain.model.PtySize
import com.agychat.app.domain.model.PtyConnectionState

@Singleton
class PtyBridgeService @Inject constructor(
    private val spawner: PtyProcessSpawner,
    private val inputWriter: PtyInputWriter,
    private val outputReader: PtyOutputReader,
    private val stateHolder: PtyConnectionStateHolder,
    private val winsizeSyncer: PtyWinsizeSyncer
) {
    private var process: Process? = null

    fun startProcess(command: String, args: List<String>, cwd: String, size: PtySize): Boolean {
        return try {
            stateHolder.setState(PtyConnectionState.Connecting)
            process = spawner.spawn(command, args, emptyMap(), cwd)
            val p = process ?: throw Exception("Failed to start process")
            
            inputWriter.setOutputStream(p.outputStream)
            // Using GlobalScope or a provided scope for reading. For this stub, outputReader should have a scope passed.
            // outputReader.startReading(p.inputStream, scope)
            
            winsizeSyncer.setSize(size)
            stateHolder.setState(PtyConnectionState.Connected)
            true
        } catch (e: Exception) {
            stateHolder.setState(PtyConnectionState.Error(e.message ?: "Unknown error"))
            false
        }
    }

    fun stopProcess() {
        spawner.destroy()
        inputWriter.close()
        outputReader.stopReading()
        stateHolder.setState(PtyConnectionState.Disconnected)
        process = null
    }

    fun writeInput(bytes: ByteArray) {
        inputWriter.write(bytes)
    }

    fun observeOutput(): Flow<ByteArray> = outputReader.observeOutput()

    fun observeConnectionState(): Flow<PtyConnectionState> = stateHolder.observeState()

    fun syncWindowSize(size: PtySize) {
        winsizeSyncer.setSize(size)
    }

    fun isRunning(): Boolean {
        return process?.isAlive == true
    }
}
