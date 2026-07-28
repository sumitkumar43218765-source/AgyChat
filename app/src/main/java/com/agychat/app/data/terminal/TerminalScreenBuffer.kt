package com.agychat.app.data.terminal

import javax.inject.Inject

class TerminalScreenBuffer @Inject constructor() {
    private val lines = mutableListOf<StringBuilder>()
    var cursorRow = 0
    var cursorCol = 0
    var maxRows = 24
    var maxCols = 80

    init {
        lines.add(StringBuilder())
    }

    fun appendChar(char: Char) {
        ensureRowExists(cursorRow)
        val line = lines[cursorRow]
        
        while (line.length <= cursorCol) {
            line.append(' ')
        }
        
        line[cursorCol] = char
        cursorCol++
        
        if (cursorCol >= maxCols) {
            cursorCol = 0
            newLine()
        }
    }

    fun newLine() {
        cursorRow++
        if (cursorRow >= maxRows) {
            cursorRow = maxRows - 1
            lines.removeAt(0)
            lines.add(StringBuilder())
        } else {
            ensureRowExists(cursorRow)
        }
    }

    fun carriageReturn() {
        cursorCol = 0
    }

    fun setChar(row: Int, col: Int, char: Char) {
        if (row in 0 until maxRows && col in 0 until maxCols) {
            ensureRowExists(row)
            val line = lines[row]
            while (line.length <= col) {
                line.append(' ')
            }
            line[col] = char
        }
    }

    fun getLine(row: Int): String {
        return if (row in lines.indices) lines[row].toString() else ""
    }

    fun clear() {
        lines.clear()
        lines.add(StringBuilder())
        cursorRow = 0
        cursorCol = 0
    }

    fun toSnapshot(): TerminalScreenSnapshot {
        return TerminalScreenSnapshot(lines.map { it.toString() }, cursorRow, cursorCol)
    }
    
    private fun ensureRowExists(row: Int) {
        while (lines.size <= row) {
            lines.add(StringBuilder())
        }
    }
}
