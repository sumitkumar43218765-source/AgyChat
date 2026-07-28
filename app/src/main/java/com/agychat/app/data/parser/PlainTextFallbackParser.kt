package com.agychat.app.data.parser

import javax.inject.Inject

data class AssistantTextContent(val text: String)

class PlainTextFallbackParser @Inject constructor() {
    fun parse(line: String): AssistantTextContent {
        return AssistantTextContent(line)
    }
}
