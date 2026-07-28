package com.agychat.app.domain.model

/**
 * Represents a delta or change in terminal lines.
 *
 * @property newLines The newly added or modified lines.
 * @property removedLineCount Number of lines removed.
 * @property startLineIndex The index where the changes start.
 */
data class TerminalLineDelta(
    val newLines: List<String>,
    val removedLineCount: Int,
    val startLineIndex: Int
)
