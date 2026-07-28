package com.agychat.app.domain.usecase.artifact

import com.agychat.app.domain.repository.ArtifactRepository
import javax.inject.Inject

/**
 * Use case to start watching artifacts for a conversation.
 */
class StartArtifactWatcherUseCase @Inject constructor(
    private val artifactRepo: ArtifactRepository
) {
    suspend operator fun invoke(conversationUuid: String) {
        artifactRepo.startWatching(conversationUuid)
    }
}
