package com.agychat.app.data.parser.pattern

object DiffPreviewPattern {
    val LINE_REGEX = Regex("""^\s*(\d+)\s+([+\-])\s+(.*)""")
    val HIDDEN_REGEX = Regex("""^\s*\.\.\.\s*(\d+)\s+more lines""")
}
