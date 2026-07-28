package com.agychat.app.data.terminal

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class TerminalIdleSettleDetector @Inject constructor() {
    private val _settleEvents = MutableSharedFlow<Unit>()
    private var debounceJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun onBytesReceived() {
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(50) // wait for 50ms of idle time
            _settleEvents.emit(Unit)
        }
    }

    suspend fun awaitSettle(delayMs: Long): Boolean {
        return try {
            withTimeout(delayMs) {
                _settleEvents.collect {
                    // settled
                    throw CancellationException("Settled")
                }
            }
            false
        } catch (e: CancellationException) {
            if (e.message == "Settled") true else throw e
        }
    }

    fun observeSettleEvents(): Flow<Unit> = _settleEvents
}
