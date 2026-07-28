package com.agychat.app.domain.usecase.parser

import com.agychat.app.domain.model.ParsedEvent
import javax.inject.Inject

/**
 * Use case to detect a thinking block from a string line.
 */
class DetectThinkingBlockUseCase @Inject constructor() {
    operator fun invoke(line: String): ParsedEvent.ThinkingBlock? {
        // Implementation omitted for skeleton
        return null
    }
}
