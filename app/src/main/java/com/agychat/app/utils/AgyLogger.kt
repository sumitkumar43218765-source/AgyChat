package com.agychat.app.utils

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AgyLogger {
    private const val TAG = "AgyChatApp"
    private var logFile: File? = null

    fun init() {
        try {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            logFile = File(downloadsDir, "agychat_debug.log")
            if (logFile?.exists() == false) {
                logFile?.createNewFile()
            }
            
            // Capture uncaught exceptions for crashes
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
                e("CRASH", "Uncaught exception in thread ${thread.name}", throwable)
                defaultHandler?.uncaughtException(thread, throwable)
            }
            
            i("System", "AgyLogger initialized. App started.")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to init AgyLogger", e)
        }
    }

    fun d(tag: String, message: String) = write("DEBUG", tag, message)
    fun i(tag: String, message: String) = write("INFO", tag, message)
    fun e(tag: String, message: String, t: Throwable? = null) {
        val stackTrace = t?.stackTraceToString() ?: ""
        write("ERROR", tag, "$message\n$stackTrace")
    }

    private fun write(level: String, tag: String, message: String) {
        // Still print to standard Logcat for local android studio debugging if needed
        Log.println(if (level == "ERROR") Log.ERROR else Log.DEBUG, tag, message)
        try {
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(Date())
            val logMessage = "[$timestamp] [$level] [$tag] $message\n"
            logFile?.let {
                FileWriter(it, true).use { writer ->
                    writer.append(logMessage)
                }
            }
        } catch (e: Exception) {
            // Ignore if we can't write to the file
        }
    }
}
