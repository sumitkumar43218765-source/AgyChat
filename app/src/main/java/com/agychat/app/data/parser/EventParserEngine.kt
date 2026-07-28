package com.agychat.app.data.parser

import javax.inject.Inject

sealed class ParsedEvent
data class EventToolCall(val content: ToolCallContent) : ParsedEvent()
data class EventThinking(val content: ThinkingBlockContent) : ParsedEvent()
data class EventStatus(val content: StatusLineContent) : ParsedEvent()
data class EventResume(val content: SessionResumeInfo) : ParsedEvent()
data class EventText(val content: AssistantTextContent) : ParsedEvent()

class EventParserEngine @Inject constructor(
    private val toolCallParser: ToolCallParser,
    private val thinkingBlockParser: ThinkingBlockParser,
    private val statusLineParser: StatusLineParser,
    private val sessionResumeHintParser: SessionResumeHintParser,
    private val plainTextFallbackParser: PlainTextFallbackParser
) {
    fun parseLines(lines: List<String>): List<ParsedEvent> {
        val events = mutableListOf<ParsedEvent>()
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            val nextLine = lines.getOrNull(i + 1)

            val toolCall = toolCallParser.parse(line)
            if (toolCall != null) {
                events.add(EventToolCall(toolCall))
                i++
                continue
            }

            val thinking = thinkingBlockParser.parse(line, nextLine)
            if (thinking != null) {
                events.add(EventThinking(thinking))
                i += 2
                continue
            }

            val status = statusLineParser.parse(line)
            if (status != null) {
                events.add(EventStatus(status))
                i++
                continue
            }

            val resume = sessionResumeHintParser.parse(line)
            if (resume != null) {
                events.add(EventResume(resume))
                i++
                continue
            }

            events.add(EventText(plainTextFallbackParser.parse(line)))
            i++
        }
        return events
    }
}
