package com.agychat.app.di

import com.agychat.app.data.artifact.BrainFolderFileObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtifactWatcherModule {

    @Provides
    @Singleton
    fun provideBrainFolderFileObserver(): BrainFolderFileObserver {
        return BrainFolderFileObserver()
    }
}
