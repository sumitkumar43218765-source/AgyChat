package com.agychat.app.domain.model

/**
 * Represents a chat session or thread in the application.
 *
 * @property id Unique identifier for the session.
 * @property title Title of the session.
 * @property conversationUuid UUID of the remote conversation, if applicable.
 * @property workspaceId Identifier of the associated workspace, if any.
 * @property createdAt Timestamp when the session was created.
 * @property updatedAt Timestamp of the last update to the session.
 * @property isActive Indicates if the session is currently active.
 */
data class ChatSession(
    val id: String,
    val title: String,
    val conversationUuid: String?,
    val workspaceId: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)
