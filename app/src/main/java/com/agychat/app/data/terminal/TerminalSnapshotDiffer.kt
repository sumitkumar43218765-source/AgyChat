package com.agychat.app.data.terminal

import javax.inject.Inject

class TerminalSnapshotDiffer @Inject constructor() {
    fun diff(old: TerminalScreenSnapshot, new: TerminalScreenSnapshot): TerminalLineDelta {
        val changedLines = mutableMapOf<Int, String>()
        
        val maxLines = maxOf(old.lines.size, new.lines.size)
        
        for (i in 0 until maxLines) {
            val oldLine = old.lines.getOrNull(i)
            val newLine = new.lines.getOrNull(i)
            
            if (oldLine != newLine && newLine != null) {
                changedLines[i] = newLine
            }
        }
        
        return TerminalLineDelta(changedLines)
    }
}
