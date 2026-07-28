package com.agychat.app.data.parser

import com.agychat.app.data.parser.pattern.DiffPreviewPattern
import javax.inject.Inject

data class DiffPreviewContent(val lines: List<String>, val hiddenCount: Int)

class DiffPreviewParser @Inject constructor() {
    fun parse(lines: List<String>): DiffPreviewContent? {
        if (lines.isEmpty()) return null
        val diffLines = mutableListOf<String>()
        var hiddenCount = 0
        for (line in lines) {
            if (DiffPreviewPattern.LINE_REGEX.matches(line)) {
                diffLines.add(line)
            } else {
                val hiddenMatch = DiffPreviewPattern.HIDDEN_REGEX.find(line)
                if (hiddenMatch != null) {
                    hiddenCount = hiddenMatch.groupValues[1].toIntOrNull() ?: 0
                }
            }
        }
        if (diffLines.isEmpty()) return null
        return DiffPreviewContent(diffLines, hiddenCount)
    }
}
