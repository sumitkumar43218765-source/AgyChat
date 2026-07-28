package com.agychat.app.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for settings.
 */
interface SettingsRepository {
    fun getAgyBinaryPath(): Flow<String>
    suspend fun setAgyBinaryPath(path: String)
    fun getIdleSettleDelay(): Flow<Long>
    suspend fun setIdleSettleDelay(delayMs: Long)
    suspend fun validateAgyInstallation(path: String): Boolean
}
