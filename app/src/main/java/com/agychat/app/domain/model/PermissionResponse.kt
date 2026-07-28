package com.agychat.app.domain.model

/**
 * Represents the user's response to a permission prompt.
 *
 * @property selectedIndex The index of the selected option.
 * @property optionText The text of the selected option.
 */
data class PermissionResponse(
    val selectedIndex: Int,
    val optionText: String
)
