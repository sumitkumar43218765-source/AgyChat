package com.agychat.app.util

object RegexPatterns {
    val ANSI_ESCAPE = Regex("""\x1b\[[0-9;]*[a-zA-Z]""")
    val UUID_PATTERN = Regex("""[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}""")
}
