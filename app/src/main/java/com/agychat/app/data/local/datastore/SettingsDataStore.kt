package com.agychat.app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getAgyBinaryPath(): Flow<String> = context.settingsDataStore.data.map { preferences ->
        preferences[SettingsKeys.AGY_BINARY_PATH] ?: "/data/data/com.termux/files/usr/bin/agy"
    }

    suspend fun setAgyBinaryPath(path: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsKeys.AGY_BINARY_PATH] = path
        }
    }

    fun getIdleSettleDelay(): Flow<Long> = context.settingsDataStore.data.map { preferences ->
        preferences[SettingsKeys.IDLE_SETTLE_DELAY] ?: 100L
    }

    suspend fun setIdleSettleDelay(delayMs: Long) {
        context.settingsDataStore.edit { preferences ->
            preferences[SettingsKeys.IDLE_SETTLE_DELAY] = delayMs
        }
    }
}
