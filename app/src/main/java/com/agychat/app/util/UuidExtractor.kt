package com.agychat.app.util

object UuidExtractor {
    fun extractFromLine(line: String): String? {
        val match = RegexPatterns.UUID_PATTERN.find(line)
        return match?.value
    }
}
