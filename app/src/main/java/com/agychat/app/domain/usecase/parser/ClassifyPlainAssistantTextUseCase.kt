package com.agychat.app.domain.usecase.parser

import com.agychat.app.domain.model.ParsedEvent
import com.agychat.app.domain.model.AssistantTextContent
import javax.inject.Inject

/**
 * Use case to classify plain assistant text.
 */
class ClassifyPlainAssistantTextUseCase @Inject constructor() {
    operator fun invoke(line: String): ParsedEvent.AssistantText {
        return ParsedEvent.AssistantText(AssistantTextContent(line))
    }
}
