package com.agychat.app.domain.repository

import com.agychat.app.domain.model.PlanArtifact
import com.agychat.app.domain.model.TaskArtifact
import com.agychat.app.domain.model.WalkthroughArtifact
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing artifacts.
 */
interface ArtifactRepository {
    suspend fun startWatching(conversationUuid: String)
    suspend fun stopWatching()
    fun observeTaskArtifact(): Flow<TaskArtifact?>
    fun observePlanArtifact(): Flow<PlanArtifact?>
    fun observeWalkthroughArtifact(): Flow<WalkthroughArtifact?>
    suspend fun locateBrainFolder(conversationUuid: String): String?
}
