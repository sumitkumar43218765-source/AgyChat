package com.agychat.app.domain.usecase.artifact

import com.agychat.app.domain.model.ArtifactMetadata
import javax.inject.Inject

/**
 * Use case to parse Artifact metadata from a file.
 */
class ParseArtifactMetadataUseCase @Inject constructor() {
    suspend operator fun invoke(filePath: String): ArtifactMetadata? {
        // Implement parsing logic
        return null
    }
}
