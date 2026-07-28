package com.agychat.app.domain.model

/**
 * Represents a single line within a diff preview.
 *
 * @property lineNumber The line number in the file.
 * @property content The text content of the line.
 * @property type The type of diff operation for this line.
 */
data class DiffLine(
    val lineNumber: Int,
    val content: String,
    val type: DiffLineType
)
