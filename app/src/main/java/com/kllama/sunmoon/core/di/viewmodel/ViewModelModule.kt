package com.kllama.sunmoon.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kllama.sunmoon.ui.shuttle.ShuttleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ShuttleViewModel::class)
    abstract fun bindShuttleViewModel(vm: ShuttleViewModel): ViewModel
}