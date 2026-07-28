package com.agychat.app.domain.usecase.artifact

import com.agychat.app.domain.repository.ArtifactRepository
import javax.inject.Inject

/**
 * Use case to stop watching artifacts.
 */
class StopArtifactWatcherUseCase @Inject constructor(
    private val artifactRepo: ArtifactRepository
) {
    suspend operator fun invoke() {
        artifactRepo.stopWatching()
    }
}
