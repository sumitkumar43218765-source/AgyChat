package com.agychat.app.data.artifact

import android.os.FileObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject

class BrainFolderFileObserver @Inject constructor() {
    private var observer: FileObserver? = null

    fun observeChanges(path: String): Flow<String> = callbackFlow {
        observer = object : FileObserver(File(path), MODIFY or CREATE or DELETE) {
            override fun onEvent(event: Int, file: String?) {
                if (file != null) {
                    trySend(file)
                }
            }
        }
        observer?.startWatching()

        awaitClose {
            observer?.stopWatching()
            observer = null
        }
    }
    
    fun startWatching(path: String) {
        observer?.stopWatching()
        observer = object : FileObserver(File(path), MODIFY or CREATE or DELETE) {
            override fun onEvent(event: Int, file: String?) {}
        }
        observer?.startWatching()
    }
    
    fun stopWatching() {
        observer?.stopWatching()
        observer = null
    }
}
