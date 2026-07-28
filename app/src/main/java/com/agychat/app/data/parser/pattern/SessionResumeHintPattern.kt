package com.agychat.app.data.parser.pattern

object SessionResumeHintPattern {
    val REGEX = Regex("""agy\s+--conversation=([a-f0-9\-]+)""")
}
