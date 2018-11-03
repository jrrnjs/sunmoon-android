package com.kllama.sunmoon.core.di

import com.kllama.sunmoon.SMApplication
import com.kllama.sunmoon.core.di.viewmodel.ViewModelModule
import com.kllama.sunmoon.ui.MainActivity
import com.kllama.sunmoon.ui.shuttle.train.ShuttleTrainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun inject(application: SMApplication)

    fun inject(fragment: ShuttleTrainFragment)
}