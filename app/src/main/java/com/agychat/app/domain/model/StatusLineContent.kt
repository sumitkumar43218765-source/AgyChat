package com.agychat.app.domain.model

/**
 * Represents status information about the current operation.
 *
 * @property modelName The name of the AI model being used.
 * @property effortLevel The configured effort level.
 */
data class StatusLineContent(
    val modelName: String,
    val effortLevel: String
)
