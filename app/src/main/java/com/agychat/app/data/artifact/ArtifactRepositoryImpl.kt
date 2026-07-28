package com.agychat.app.data.artifact

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface ArtifactRepository {
    fun observeArtifacts(path: String): Flow<String>
}

class ArtifactRepositoryImpl @Inject constructor(
    private val observer: BrainFolderFileObserver,
    private val taskParser: TaskMarkdownParser,
    private val planParser: PlanMarkdownParser,
    private val walkthroughParser: WalkthroughMarkdownParser,
    private val metadataParser: ArtifactMetadataJsonParser
) : ArtifactRepository {

    override fun observeArtifacts(path: String): Flow<String> {
        return observer.observeChanges(path)
    }
}
