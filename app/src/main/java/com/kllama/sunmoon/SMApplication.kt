package com.kllama.sunmoon

import android.app.Application
import com.kllama.sunmoon.core.di.ApplicationComponent
import com.kllama.sunmoon.core.di.ApplicationModule
import com.kllama.sunmoon.core.di.DaggerApplicationComponent
import timber.log.Timber

class SMApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent.inject(this)
    }
}