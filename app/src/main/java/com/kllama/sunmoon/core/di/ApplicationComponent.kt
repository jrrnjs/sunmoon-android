package com.kllama.sunmoon.core.di

import com.kllama.sunmoon.SMApplication
import com.kllama.sunmoon.core.di.viewmodel.ViewModelModule
import com.kllama.sunmoon.ui.shuttle.ShuttleFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun inject(application: SMApplication)

    fun inject(fragment: ShuttleFragment)
}