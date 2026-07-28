package com.agychat.app.domain.model

/**
 * Represents information about an agent model.
 *
 * @property modelName Name of the model.
 * @property effortLevel Configured effort level for the model.
 */
data class AgentModelInfo(
    val modelName: String,
    val effortLevel: String
)
