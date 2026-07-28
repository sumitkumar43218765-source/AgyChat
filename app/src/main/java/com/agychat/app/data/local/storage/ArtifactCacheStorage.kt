package com.agychat.app.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

data class ArtifactCacheDto(
    val conversationUuid: String,
    val content: String,
    val lastUpdated: Long
)

private val Context.artifactCacheDataStore by preferencesDataStore(name = "artifact_caches")

@Singleton
class ArtifactCacheStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val key = stringPreferencesKey("artifact_caches_map_json")
    private val gson = Gson()
    private val type = object : TypeToken<Map<String, ArtifactCacheDto>>() {}.type

    suspend fun getForConversation(conversationUuid: String): ArtifactCacheDto? {
        return try {
            val prefs = context.artifactCacheDataStore.data.first()
            val json = prefs[key]
            val map: Map<String, ArtifactCacheDto> = if (json.isNullOrEmpty()) emptyMap() else gson.fromJson(json, type)
            map[conversationUuid]
        } catch (e: Exception) {
            null
        }
    }

    suspend fun save(cache: ArtifactCacheDto) {
        context.artifactCacheDataStore.edit { prefs ->
            val json = prefs[key]
            val map: MutableMap<String, ArtifactCacheDto> = if (json.isNullOrEmpty()) mutableMapOf() else gson.fromJson(json, type)
            map[cache.conversationUuid] = cache
            prefs[key] = gson.toJson(map)
        }
    }

    suspend fun clear(conversationUuid: String) {
        context.artifactCacheDataStore.edit { prefs ->
            val json = prefs[key]
            val map: MutableMap<String, ArtifactCacheDto> = if (json.isNullOrEmpty()) mutableMapOf() else gson.fromJson(json, type)
            if (map.remove(conversationUuid) != null) {
                prefs[key] = gson.toJson(map)
            }
        }
    }
}
