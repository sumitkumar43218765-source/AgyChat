package com.agychat.app.domain.usecase.artifact

import com.agychat.app.domain.model.PlanArtifact
import javax.inject.Inject

/**
 * Use case to parse a Plan artifact from a file.
 */
class ParsePlanArtifactUseCase @Inject constructor() {
    suspend operator fun invoke(filePath: String): PlanArtifact? {
        // Implement parsing logic
        return null
    }
}
