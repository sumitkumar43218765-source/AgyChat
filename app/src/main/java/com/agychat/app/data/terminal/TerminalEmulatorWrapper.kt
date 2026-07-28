package com.agychat.app.data.terminal

import javax.inject.Inject
import javax.inject.Singleton

data class TerminalScreenSnapshot(val lines: List<String>, val cursorRow: Int, val cursorCol: Int)
data class TerminalLineDelta(val changedLines: Map<Int, String>)

@Singleton
class TerminalEmulatorWrapper @Inject constructor(
    private val buffer: TerminalScreenBuffer
) {
    fun feedBytes(bytes: ByteArray) {
        val text = String(bytes)
        for (char in text) {
            when (char) {
                '\n' -> buffer.newLine()
                '\r' -> buffer.carriageReturn()
                // Basic ANSI handling can be added here
                else -> buffer.appendChar(char)
            }
        }
    }

    fun getScreenLines(): List<String> {
        return buffer.toSnapshot().lines
    }

    fun getCursorPosition(): Pair<Int, Int> {
        return Pair(buffer.cursorRow, buffer.cursorCol)
    }

    fun reset() {
        buffer.clear()
    }
    
    fun getSnapshot(): TerminalScreenSnapshot {
        return buffer.toSnapshot()
    }
}
