package com.agychat.app.domain.model

/**
 * Represents a preview of a file diff.
 *
 * @property filePath The path of the file being modified.
 * @property lines The list of diff lines.
 * @property totalHiddenLines Number of unchanged lines not shown.
 * @property isExpanded Whether the diff view is expanded.
 */
data class DiffPreviewContent(
    val filePath: String,
    val lines: List<DiffLine>,
    val totalHiddenLines: Int,
    val isExpanded: Boolean = false
)
