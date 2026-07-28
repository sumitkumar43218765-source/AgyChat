package com.agychat.app.domain.model

/**
 * Metadata associated with an artifact.
 *
 * @property conversationId ID of the conversation this artifact belongs to.
 * @property timestamp Time when the artifact was created or modified.
 * @property type The type of the artifact.
 * @property filePath The file path where the artifact is stored, if applicable.
 */
data class ArtifactMetadata(
    val conversationId: String,
    val timestamp: Long,
    val type: ArtifactType,
    val filePath: String
)
