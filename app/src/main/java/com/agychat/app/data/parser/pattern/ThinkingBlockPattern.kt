package com.agychat.app.data.parser.pattern

object ThinkingBlockPattern {
    val REGEX = Regex("""^\s*►\s+Thought for (\d+)s,\s*(\d+)\s+tokens?""")
}
