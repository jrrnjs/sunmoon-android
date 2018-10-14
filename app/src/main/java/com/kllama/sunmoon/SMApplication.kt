package com.kllama.sunmoon

import android.app.Application
import timber.log.Timber

class SMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}