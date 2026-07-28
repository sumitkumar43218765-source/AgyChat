package com.agychat.app.domain.usecase.parser

import com.agychat.app.domain.model.ParsedEvent
import javax.inject.Inject

/**
 * Use case to detect a permission prompt from lines.
 */
class DetectPermissionPromptUseCase @Inject constructor() {
    operator fun invoke(lines: List<String>): ParsedEvent.PermissionPrompt? {
        // Implementation omitted for skeleton
        return null
    }
}
