package com.agychat.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import com.agychat.app.utils.AgyLogger

@HiltAndroidApp
class AgyChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AgyLogger.init()
    }
}
