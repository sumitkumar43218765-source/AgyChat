package com.agychat.app.data.parser.pattern

object PermissionPromptPattern {
    val QUESTION_REGEX = Regex("""^(.+\?)\s*$""")
    val OPTION_REGEX = Regex("""^\s*(>?)\s*(\d+)\.\s+(.+)$""")
}
