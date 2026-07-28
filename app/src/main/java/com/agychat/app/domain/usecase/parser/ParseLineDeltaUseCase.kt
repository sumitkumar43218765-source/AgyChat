package com.agychat.app.domain.usecase.parser

import com.agychat.app.domain.model.ParsedEvent
import com.agychat.app.domain.model.TerminalLineDelta
import javax.inject.Inject

/**
 * Master parser use case to parse terminal line delta into parsed events.
 */
class ParseLineDeltaUseCase @Inject constructor() {
    operator fun invoke(delta: TerminalLineDelta): List<ParsedEvent> {
        // Implementation omitted for skeleton
        return emptyList()
    }
}
