package com.agychat.app.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

internal data class ChatSessionDto(
    val id: String,
    val title: String,
    val timestamp: Long,
    val workspaceId: String? = null
)

private val Context.chatSessionDataStore by preferencesDataStore(name = "chat_sessions")

@Singleton
class ChatSessionStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val key = stringPreferencesKey("sessions_json")
    private val gson = Gson()
    private val type = object : TypeToken<List<ChatSessionDto>>() {}.type

    fun getAll(): Flow<List<ChatSessionDto>> = context.chatSessionDataStore.data.map { prefs ->
        val json = prefs[key]
        if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
    }

    suspend fun save(session: ChatSessionDto) {
        context.chatSessionDataStore.edit { prefs ->
            val currentJson = prefs[key]
            val currentList: MutableList<ChatSessionDto> = if (currentJson.isNullOrEmpty()) {
                mutableListOf()
            } else {
                gson.fromJson(currentJson, type)
            }
            if (!currentList.any { it.id == session.id }) {
                currentList.add(session)
                prefs[key] = gson.toJson(currentList)
            }
        }
    }

    suspend fun update(session: ChatSessionDto) {
        context.chatSessionDataStore.edit { prefs ->
            val currentJson = prefs[key]
            val currentList: MutableList<ChatSessionDto> = if (currentJson.isNullOrEmpty()) {
                mutableListOf()
            } else {
                gson.fromJson(currentJson, type)
            }
            val index = currentList.indexOfFirst { it.id == session.id }
            if (index != -1) {
                currentList[index] = session
                prefs[key] = gson.toJson(currentList)
            }
        }
    }

    suspend fun delete(id: String) {
        context.chatSessionDataStore.edit { prefs ->
            val currentJson = prefs[key]
            val currentList: MutableList<ChatSessionDto> = if (currentJson.isNullOrEmpty()) {
                mutableListOf()
            } else {
                gson.fromJson(currentJson, type)
            }
            val removed = currentList.removeAll { it.id == id }
            if (removed) {
                prefs[key] = gson.toJson(currentList)
            }
        }
    }
}
