package com.agychat.app.data.terminal

import com.agychat.app.data.pty.PtyOutputReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TerminalLineDeltaEmitter @Inject constructor(
    private val ptyOutputReader: PtyOutputReader,
    private val emulatorWrapper: TerminalEmulatorWrapper,
    private val settleDetector: TerminalIdleSettleDetector,
    private val snapshotDiffer: TerminalSnapshotDiffer
) {
    private val _deltas = MutableSharedFlow<TerminalLineDelta>()
    private var emissionJob: Job? = null
    private var lastSnapshot = TerminalScreenSnapshot(emptyList(), 0, 0)

    fun start(scope: CoroutineScope) {
        stop()
        emissionJob = scope.launch {
            // Read output and feed to emulator
            launch {
                ptyOutputReader.observeOutput().collect { bytes ->
                    emulatorWrapper.feedBytes(bytes)
                    settleDetector.onBytesReceived()
                }
            }
            
            // Wait for settle, snapshot and diff
            launch {
                settleDetector.observeSettleEvents().collect {
                    val newSnapshot = emulatorWrapper.getSnapshot()
                    val delta = snapshotDiffer.diff(lastSnapshot, newSnapshot)
                    if (delta.changedLines.isNotEmpty()) {
                        _deltas.emit(delta)
                    }
                    lastSnapshot = newSnapshot
                }
            }
        }
    }

    fun stop() {
        emissionJob?.cancel()
        emissionJob = null
    }

    fun observeLineDeltas(): Flow<TerminalLineDelta> = _deltas
}
