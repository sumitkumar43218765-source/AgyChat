package com.agychat.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Dummy interfaces for DI compilation
interface ChatSessionStorage
interface ChatMessageStorage
interface WorkspaceStorage
interface ArtifactCacheStorage
interface SettingsDataStore

class ChatSessionStorageImpl : ChatSessionStorage
class ChatMessageStorageImpl : ChatMessageStorage
class WorkspaceStorageImpl : WorkspaceStorage
class ArtifactCacheStorageImpl : ArtifactCacheStorage
class SettingsDataStoreImpl : SettingsDataStore

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideChatSessionStorage(): ChatSessionStorage = ChatSessionStorageImpl()

    @Provides
    @Singleton
    fun provideChatMessageStorage(): ChatMessageStorage = ChatMessageStorageImpl()

    @Provides
    @Singleton
    fun provideWorkspaceStorage(): WorkspaceStorage = WorkspaceStorageImpl()

    @Provides
    @Singleton
    fun provideArtifactCacheStorage(): ArtifactCacheStorage = ArtifactCacheStorageImpl()

    @Provides
    @Singleton
    fun provideSettingsDataStore(): SettingsDataStore = SettingsDataStoreImpl()
}
