package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.StatusLinePattern
import javax.inject.Inject

data class StatusLineContent(val model: String, val effort: String)

class StatusLineParser @Inject constructor() {
    fun parse(line: String): StatusLineContent? {
        val match = StatusLinePattern.REGEX.find(line) ?: return null
        return StatusLineContent(match.groupValues[1], match.groupValues[2])
    }
}
