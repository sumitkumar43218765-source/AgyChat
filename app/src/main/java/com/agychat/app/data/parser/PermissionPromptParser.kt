package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.PermissionPromptPattern
import javax.inject.Inject

data class PermissionOption(val index: Int, val text: String, val isHighlighted: Boolean)
data class PermissionPromptContent(val question: String, val options: List<PermissionOption>, val highlightedIndex: Int)

class PermissionPromptParser @Inject constructor() {
    fun parse(lines: List<String>): PermissionPromptContent? {
        var question = ""
        val options = mutableListOf<PermissionOption>()
        var highlightedIndex = -1

        for (line in lines) {
            val qMatch = PermissionPromptPattern.QUESTION_REGEX.find(line)
            if (qMatch != null) {
                question = qMatch.groupValues[1]
                continue
            }
            val oMatch = PermissionPromptPattern.OPTION_REGEX.find(line)
            if (oMatch != null) {
                val isHighlighted = oMatch.groupValues[1] == ">"
                val idx = oMatch.groupValues[2].toIntOrNull() ?: 0
                val text = oMatch.groupValues[3]
                options.add(PermissionOption(idx, text, isHighlighted))
                if (isHighlighted) highlightedIndex = idx
            }
        }
        
        if (question.isEmpty() && options.isEmpty()) return null
        return PermissionPromptContent(question, options, highlightedIndex)
    }
}
