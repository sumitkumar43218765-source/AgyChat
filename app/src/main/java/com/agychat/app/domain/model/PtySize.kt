package com.agychat.app.domain.model

/**
 * Represents the size dimensions of a PTY.
 *
 * @property rows Number of rows.
 * @property cols Number of columns.
 */
data class PtySize(
    val rows: Int,
    val cols: Int
)
