package com.agychat.app.domain.usecase.parser

import com.agychat.app.domain.model.ParsedEvent
import javax.inject.Inject

/**
 * Use case to detect a tool call block from a string line.
 */
class DetectToolCallUseCase @Inject constructor() {
    operator fun invoke(line: String): ParsedEvent.ToolCall? {
        // Implementation omitted for skeleton
        return null
    }
}
