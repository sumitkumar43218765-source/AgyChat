package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.ThinkingBlockPattern
import javax.inject.Inject

data class ThinkingBlockContent(val duration: Int, val tokens: Int, val summary: String?)

class ThinkingBlockParser @Inject constructor() {
    fun parse(line: String, nextLine: String?): ThinkingBlockContent? {
        val match = ThinkingBlockPattern.REGEX.find(line) ?: return null
        val duration = match.groupValues[1].toIntOrNull() ?: 0
        val tokens = match.groupValues[2].toIntOrNull() ?: 0
        return ThinkingBlockContent(duration, tokens, nextLine?.trim())
    }
}
