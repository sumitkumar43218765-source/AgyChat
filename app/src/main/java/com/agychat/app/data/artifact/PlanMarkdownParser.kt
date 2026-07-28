package com.agychat.app.data.artifact

import javax.inject.Inject

data class PlanArtifact(val content: String)

class PlanMarkdownParser @Inject constructor() {
    fun parse(content: String): PlanArtifact? {
        if (content.isBlank()) return null
        return PlanArtifact(content)
    }
}
