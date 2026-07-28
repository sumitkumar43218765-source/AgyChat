package com.agychat.app.data.artifact

import com.google.gson.Gson
import javax.inject.Inject

data class ArtifactMetadata(val id: String, val type: String, val timestamp: Long)

class ArtifactMetadataJsonParser @Inject constructor() {
    private val gson = Gson()
    
    fun parse(jsonContent: String): ArtifactMetadata? {
        return try {
            gson.fromJson(jsonContent, ArtifactMetadata::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
