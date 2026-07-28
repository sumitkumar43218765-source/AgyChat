package com.agychat.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * This module is OPTIONAL since Hilt can auto-inject use cases via @Inject constructor.
 * It is created here as a placeholder for any manual UseCase bindings if they interface-based.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    // Add use case providers/binds here if needed
}
