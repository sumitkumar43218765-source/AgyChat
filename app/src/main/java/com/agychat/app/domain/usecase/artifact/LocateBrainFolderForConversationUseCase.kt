package com.agychat.app.domain.usecase.artifact

import com.agychat.app.domain.repository.ArtifactRepository
import javax.inject.Inject

/**
 * Use case to locate the brain folder for a conversation.
 */
class LocateBrainFolderForConversationUseCase @Inject constructor(
    private val artifactRepo: ArtifactRepository
) {
    suspend operator fun invoke(conversationUuid: String): String? {
        return artifactRepo.locateBrainFolder(conversationUuid)
    }
}
