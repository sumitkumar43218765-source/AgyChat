package com.agychat.app.data.pty

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.runBlocking
import java.io.OutputStream
import javax.inject.Inject

class PtyInputWriter @Inject constructor() {
    private var outputStream: OutputStream? = null
    private val mutex = Mutex()

    fun setOutputStream(outputStream: OutputStream) {
        this.outputStream = outputStream
    }

    fun write(bytes: ByteArray) = runBlocking {
        mutex.withLock {
            try {
                outputStream?.write(bytes)
                outputStream?.flush()
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun writeString(text: String) {
        write(text.toByteArray())
    }

    fun writeEscapeSequence(sequence: ByteArray) {
        write(sequence)
    }

    fun close() = runBlocking {
        mutex.withLock {
            try {
                outputStream?.close()
            } catch (e: Exception) {}
            outputStream = null
        }
    }
}
