package com.agychat.app.data.artifact

import com.agychat.app.domain.repository.ArtifactRepository
import com.agychat.app.domain.model.PlanArtifact
import com.agychat.app.domain.model.TaskArtifact
import com.agychat.app.domain.model.WalkthroughArtifact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ArtifactRepositoryImpl @Inject constructor(
    private val observer: BrainFolderFileObserver,
    private val taskParser: TaskMarkdownParser,
    private val planParser: PlanMarkdownParser,
    private val walkthroughParser: WalkthroughMarkdownParser,
    private val metadataParser: ArtifactMetadataJsonParser
) : ArtifactRepository {

    override suspend fun startWatching(conversationUuid: String) {
        // TODO: Implement
    }

    override suspend fun stopWatching() {
        // TODO: Implement
    }

    override fun observeTaskArtifact(): Flow<TaskArtifact?> {
        return emptyFlow()
    }

    override fun observePlanArtifact(): Flow<PlanArtifact?> {
        return emptyFlow()
    }

    override fun observeWalkthroughArtifact(): Flow<WalkthroughArtifact?> {
        return emptyFlow()
    }

    override suspend fun locateBrainFolder(conversationUuid: String): String? {
        return null
    }
}
