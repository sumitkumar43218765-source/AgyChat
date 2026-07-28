package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.SessionResumeHintPattern
import javax.inject.Inject

data class SessionResumeInfo(val uuid: String)

class SessionResumeHintParser @Inject constructor() {
    fun parse(line: String): SessionResumeInfo? {
        val match = SessionResumeHintPattern.REGEX.find(line) ?: return null
        return SessionResumeInfo(match.groupValues[1])
    }
}
