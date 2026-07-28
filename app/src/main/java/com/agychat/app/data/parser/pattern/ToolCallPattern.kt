package com.agychat.app.data.parser.pattern

object ToolCallPattern {
    val REGEX = Regex("""^\s*•\s+(\w+)\((.*)\)""")
}
