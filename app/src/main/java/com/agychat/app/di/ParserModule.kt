package com.agychat.app.di

import com.agychat.app.data.parser.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Provides
    @Singleton
    fun provideEventParserEngine(
        toolCallParser: ToolCallParser,
        thinkingBlockParser: ThinkingBlockParser,
        statusLineParser: StatusLineParser,
        sessionResumeHintParser: SessionResumeHintParser,
        plainTextFallbackParser: PlainTextFallbackParser
    ): EventParserEngine {
        return EventParserEngine(
            toolCallParser,
            thinkingBlockParser,
            statusLineParser,
            sessionResumeHintParser,
            plainTextFallbackParser
        )
    }
}
