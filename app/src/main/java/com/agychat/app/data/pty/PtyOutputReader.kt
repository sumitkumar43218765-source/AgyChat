package com.agychat.app.data.pty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

class PtyOutputReader @Inject constructor() {
    private val _outputFlow = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64)
    private var readingJob: Job? = null

    fun startReading(inputStream: InputStream, scope: CoroutineScope) {
        stopReading()
        readingJob = scope.launch(Dispatchers.IO) {
            val buffer = ByteArray(4096)
            try {
                while (isActive) {
                    val bytesRead = inputStream.read(buffer)
                    if (bytesRead > 0) {
                        val chunk = buffer.copyOfRange(0, bytesRead)
                        _outputFlow.emit(chunk)
                    } else if (bytesRead == -1) {
                        break
                    }
                }
            } catch (e: Exception) {
                // Stream closed or error
            }
        }
    }

    fun stopReading() {
        readingJob?.cancel()
        readingJob = null
    }

    fun observeOutput(): SharedFlow<ByteArray> = _outputFlow
}
