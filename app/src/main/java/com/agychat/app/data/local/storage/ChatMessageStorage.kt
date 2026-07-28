package com.agychat.app.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

internal data class ChatMessageDto(
    val id: String,
    val sessionId: String,
    val role: String,
    val content: String,
    val timestamp: Long
)

private val Context.chatMessageDataStore by preferencesDataStore(name = "chat_messages")

@Singleton
class ChatMessageStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val key = stringPreferencesKey("messages_map_json")
    private val gson = Gson()
    private val type = object : TypeToken<Map<String, List<ChatMessageDto>>>() {}.type

    fun getForSession(sessionId: String): Flow<List<ChatMessageDto>> = context.chatMessageDataStore.data.map { prefs ->
        val json = prefs[key]
        val map: Map<String, List<ChatMessageDto>> = if (json.isNullOrEmpty()) emptyMap() else gson.fromJson(json, type)
        map[sessionId] ?: emptyList()
    }

    suspend fun add(sessionId: String, message: ChatMessageDto) {
        context.chatMessageDataStore.edit { prefs ->
            val json = prefs[key]
            val map: MutableMap<String, List<ChatMessageDto>> = if (json.isNullOrEmpty()) mutableMapOf() else gson.fromJson(json, type)
            val list = map[sessionId]?.toMutableList() ?: mutableListOf()
            if (!list.any { it.id == message.id }) {
                list.add(message)
                map[sessionId] = list
                prefs[key] = gson.toJson(map)
            }
        }
    }

    suspend fun update(sessionId: String, message: ChatMessageDto) {
        context.chatMessageDataStore.edit { prefs ->
            val json = prefs[key]
            val map: MutableMap<String, List<ChatMessageDto>> = if (json.isNullOrEmpty()) mutableMapOf() else gson.fromJson(json, type)
            val list = map[sessionId]?.toMutableList() ?: mutableListOf()
            val index = list.indexOfFirst { it.id == message.id }
            if (index != -1) {
                list[index] = message
                map[sessionId] = list
                prefs[key] = gson.toJson(map)
            }
        }
    }

    suspend fun clearSession(sessionId: String) {
        context.chatMessageDataStore.edit { prefs ->
            val json = prefs[key]
            val map: MutableMap<String, List<ChatMessageDto>> = if (json.isNullOrEmpty()) mutableMapOf() else gson.fromJson(json, type)
            if (map.containsKey(sessionId)) {
                map.remove(sessionId)
                prefs[key] = gson.toJson(map)
            }
        }
    }

    suspend fun getLastForSession(sessionId: String): ChatMessageDto? {
        val map: Map<String, List<ChatMessageDto>> = try {
            val prefs = context.chatMessageDataStore.data.first()
            val json = prefs[key]
            if (json.isNullOrEmpty()) emptyMap() else gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyMap()
        }
        return map[sessionId]?.maxByOrNull { it.timestamp }
    }
}
