package com.agychat.app.domain.model

/**
 * Represents a block of agent's "thinking" process.
 *
 * @property durationSeconds Duration of the thinking phase in seconds.
 * @property tokenCount Number of tokens processed or generated during thinking.
 * @property summary A brief summary of the thoughts.
 */
data class ThinkingBlockContent(
    val durationSeconds: Int,
    val tokenCount: Int,
    val summary: String
)
