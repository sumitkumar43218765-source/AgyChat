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

data class WorkspaceDto(
    val id: String,
    val name: String,
    val uri: String,
    val isActive: Boolean = false
)

private val Context.workspaceDataStore by preferencesDataStore(name = "workspaces")

@Singleton
class WorkspaceStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val key = stringPreferencesKey("workspaces_json")
    private val gson = Gson()
    private val type = object : TypeToken<List<WorkspaceDto>>() {}.type

    fun getAll(): Flow<List<WorkspaceDto>> = context.workspaceDataStore.data.map { prefs ->
        val json = prefs[key]
        if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
    }

    suspend fun save(workspace: WorkspaceDto) {
        context.workspaceDataStore.edit { prefs ->
            val json = prefs[key]
            val list: MutableList<WorkspaceDto> = if (json.isNullOrEmpty()) mutableListOf() else gson.fromJson(json, type)
            val index = list.indexOfFirst { it.id == workspace.id }
            if (index != -1) {
                list[index] = workspace
            } else {
                list.add(workspace)
            }
            prefs[key] = gson.toJson(list)
        }
    }

    suspend fun delete(id: String) {
        context.workspaceDataStore.edit { prefs ->
            val json = prefs[key]
            val list: MutableList<WorkspaceDto> = if (json.isNullOrEmpty()) mutableListOf() else gson.fromJson(json, type)
            if (list.removeAll { it.id == id }) {
                prefs[key] = gson.toJson(list)
            }
        }
    }

    suspend fun setActive(id: String) {
        context.workspaceDataStore.edit { prefs ->
            val json = prefs[key]
            val list: MutableList<WorkspaceDto> = if (json.isNullOrEmpty()) mutableListOf() else gson.fromJson(json, type)
            val updated = list.map { it.copy(isActive = it.id == id) }
            prefs[key] = gson.toJson(updated)
        }
    }
}
