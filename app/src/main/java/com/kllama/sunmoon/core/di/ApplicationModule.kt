package com.kllama.sunmoon.core.di

import android.content.Context
import com.kllama.sunmoon.SMApplication
import com.kllama.sunmoon.repository.ShuttleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: SMApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideShuttleRepository(impl: ShuttleRepository.Impl): ShuttleRepository = impl
}