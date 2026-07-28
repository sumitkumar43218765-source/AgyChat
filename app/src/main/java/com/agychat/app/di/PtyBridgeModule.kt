package com.agychat.app.di

import com.agychat.app.data.actioninjector.PtyInputWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class PtyBridgeService : PtyInputWriter {
    override fun write(data: ByteArray) {
        // Mock impl for PTY bridge service
    }
}

@Module
@InstallIn(SingletonComponent::class)
object PtyBridgeModule {

    @Provides
    @Singleton
    fun providePtyBridgeService(): PtyBridgeService {
        return PtyBridgeService()
    }
    
    @Provides
    @Singleton
    fun providePtyInputWriter(service: PtyBridgeService): PtyInputWriter {
        return service
    }
}
