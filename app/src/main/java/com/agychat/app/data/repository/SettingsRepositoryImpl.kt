package com.agychat.app.data.repository

import com.agychat.app.data.local.datastore.SettingsDataStore
import com.agychat.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun getAgyBinaryPath(): Flow<String> {
        return settingsDataStore.getAgyBinaryPath()
    }

    override suspend fun setAgyBinaryPath(path: String) {
        settingsDataStore.setAgyBinaryPath(path)
    }

    override fun getIdleSettleDelay(): Flow<Long> {
        return settingsDataStore.getIdleSettleDelay()
    }

    override suspend fun setIdleSettleDelay(delayMs: Long) {
        settingsDataStore.setIdleSettleDelay(delayMs)
    }

    override suspend fun validateAgyInstallation(path: String): Boolean {
        return File(path).exists()
    }
}
