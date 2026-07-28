package com.agychat.app.data.local.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object SettingsKeys {
    val AGY_BINARY_PATH = stringPreferencesKey("agy_binary_path")
    val IDLE_SETTLE_DELAY = longPreferencesKey("idle_settle_delay")
}
