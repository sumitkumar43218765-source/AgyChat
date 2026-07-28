package com.agychat.app.domain.model

/**
 * Represents a project workspace.
 *
 * @property id Unique identifier for the workspace.
 * @property name Name of the workspace.
 * @property path File path to the workspace directory.
 * @property isActive True if this is the currently active workspace.
 * @property createdAt Timestamp when the workspace was added or created.
 */
data class ProjectWorkspace(
    val id: String,
    val name: String,
    val path: String,
    val isActive: Boolean,
    val createdAt: Long
)
