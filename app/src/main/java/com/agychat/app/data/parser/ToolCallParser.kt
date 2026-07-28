package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.ToolCallPattern
import javax.inject.Inject

data class ToolCallContent(val actionName: String, val args: String, val filePath: String?)

class ToolCallParser @Inject constructor() {
    fun parse(line: String): ToolCallContent? {
        val match = ToolCallPattern.REGEX.find(line) ?: return null
        val actionName = match.groupValues[1]
        val args = match.groupValues[2]
        val filePath = args.split(",").firstOrNull()?.trim()
        return ToolCallContent(actionName, args, filePath)
    }
}
