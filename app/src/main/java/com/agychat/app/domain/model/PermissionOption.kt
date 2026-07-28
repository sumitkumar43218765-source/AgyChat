package com.agychat.app.domain.model

/**
 * Represents an option in a permission prompt.
 *
 * @property index The index of this option.
 * @property text The display text of the option.
 * @property isHighlighted Whether this option is currently highlighted.
 */
data class PermissionOption(
    val index: Int,
    val text: String,
    val isHighlighted: Boolean
)
