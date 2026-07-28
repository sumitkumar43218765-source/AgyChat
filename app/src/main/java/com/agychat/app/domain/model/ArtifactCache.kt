package com.agychat.app.domain.model

data class ArtifactCache(
    val conversationUuid: String,
    val content: String,
    val lastUpdated: Long
)
