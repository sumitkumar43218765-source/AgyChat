package com.agychat.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class TerminalEmulatorWrapper {
    // Wrapper for Terminal Emulator functionality
}

@Module
@InstallIn(SingletonComponent::class)
object TerminalEmulatorModule {

    @Provides
    @Singleton
    fun provideTerminalEmulatorWrapper(): TerminalEmulatorWrapper {
        return TerminalEmulatorWrapper()
    }
}
