package com.agychat.app.domain.model

/**
 * Represents a prompt asking the user for permission.
 *
 * @property question The question or prompt text.
 * @property options Available choices for the user.
 * @property currentHighlightedIndex The currently highlighted option.
 * @property hasResponded Whether the user has responded.
 * @property selectedIndex The index of the selected option, if any.
 */
data class PermissionPromptContent(
    val question: String,
    val options: List<PermissionOption>,
    val currentHighlightedIndex: Int,
    val hasResponded: Boolean = false,
    val selectedIndex: Int? = null
)
