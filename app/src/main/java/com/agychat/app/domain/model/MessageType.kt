package com.agychat.app.domain.model

/**
 * Enumeration of all supported message types.
 */
enum class MessageType {
    USER,
    ASSISTANT_TEXT,
    TOOL_CALL,
    THINKING_BLOCK,
    DIFF_PREVIEW,
    PERMISSION_PROMPT,
    STATUS_LINE,
    PLAN_ARTIFACT,
    WALKTHROUGH_ARTIFACT,
    TASK_ARTIFACT
}
