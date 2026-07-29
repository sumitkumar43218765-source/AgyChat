package com.agychat.app.di

import com.agychat.app.data.artifact.ArtifactRepositoryImpl
import com.agychat.app.data.pty.PtyBridgeRepositoryImpl
import com.agychat.app.data.repository.ChatMessageRepositoryImpl
import com.agychat.app.data.repository.ChatSessionRepositoryImpl
import com.agychat.app.data.repository.WorkspaceRepositoryImpl
import com.agychat.app.domain.repository.ArtifactRepository
import com.agychat.app.domain.repository.ChatMessageRepository
import com.agychat.app.domain.repository.ChatSessionRepository
import com.agychat.app.domain.repository.PtyBridgeRepository
import com.agychat.app.domain.repository.WorkspaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArtifactRepository(
        impl: ArtifactRepositoryImpl
    ): ArtifactRepository

    @Binds
    @Singleton
    abstract fun bindChatMessageRepository(
        impl: ChatMessageRepositoryImpl
    ): ChatMessageRepository

    @Binds
    @Singleton
    abstract fun bindChatSessionRepository(
        impl: ChatSessionRepositoryImpl
    ): ChatSessionRepository

    @Binds
    @Singleton
    abstract fun bindPtyBridgeRepository(
        impl: PtyBridgeRepositoryImpl
    ): PtyBridgeRepository

    @Binds
    @Singleton
    abstract fun bindWorkspaceRepository(
        impl: WorkspaceRepositoryImpl
    ): WorkspaceRepository
}
