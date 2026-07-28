package com.agychat.app.domain.model

/**
 * Represents a tool call invoked by the agent.
 *
 * @property actionName Name of the action being called.
 * @property args Arguments passed to the tool call.
 * @property filePath Optional path to the file being acted upon.
 * @property status Current status of the tool call.
 * @property rawLine The raw string representation of the tool call.
 */
data class ToolCallContent(
    val actionName: String,
    val args: String,
    val filePath: String?,
    val status: ToolCallStatus,
    val rawLine: String
)
