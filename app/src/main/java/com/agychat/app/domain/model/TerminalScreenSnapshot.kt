package com.agychat.app.domain.model

/**
 * Represents a snapshot of the terminal screen state.
 *
 * @property lines The text lines on the screen.
 * @property cursorRow The row position of the cursor.
 * @property cursorCol The column position of the cursor.
 * @property timestamp The time the snapshot was captured.
 */
data class TerminalScreenSnapshot(
    val lines: List<String>,
    val cursorRow: Int,
    val cursorCol: Int,
    val timestamp: Long
)
