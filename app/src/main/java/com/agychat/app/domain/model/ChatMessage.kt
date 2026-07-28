package com.agychat.app.domain.model

/**
 * Represents a single message in a chat session.
 *
 * @property id Unique identifier for the message.
 * @property sessionId ID of the session this message belongs to.
 * @property type Type of the message.
 * @property content JSON serialized content of the message.
 * @property timestamp Time the message was sent or received.
 * @property isStreaming True if the message is currently being streamed.
 */
data class ChatMessage(
    val id: String,
    val sessionId: String,
    val type: MessageType,
    val content: String,
    val timestamp: Long,
    val isStreaming: Boolean = false
)
