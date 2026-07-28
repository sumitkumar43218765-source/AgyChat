package com.agychat.app.domain.model

/**
 * Information needed to resume a session.
 *
 * @property conversationUuid UUID of the conversation to resume.
 * @property commandHint A command hint or instruction for resumption.
 */
data class SessionResumeInfo(
    val conversationUuid: String,
    val commandHint: String
)
