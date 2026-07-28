package com.agychat.app.di

import com.agychat.app.data.artifact.ArtifactRepository
import com.agychat.app.data.artifact.ArtifactRepositoryImpl
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
}
