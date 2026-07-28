package com.agychat.app.data.artifact

import javax.inject.Inject

data class TaskArtifact(val content: String)

class TaskMarkdownParser @Inject constructor() {
    fun parse(content: String): TaskArtifact? {
        if (content.isBlank()) return null
        return TaskArtifact(content)
    }
}
