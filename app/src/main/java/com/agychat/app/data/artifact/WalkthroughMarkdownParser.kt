package com.agychat.app.data.artifact

import javax.inject.Inject

data class WalkthroughArtifact(val content: String)

class WalkthroughMarkdownParser @Inject constructor() {
    fun parse(content: String): WalkthroughArtifact? {
        if (content.isBlank()) return null
        return WalkthroughArtifact(content)
    }
}
