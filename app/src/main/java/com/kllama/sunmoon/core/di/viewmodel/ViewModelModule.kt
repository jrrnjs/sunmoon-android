package com.kllama.sunmoon.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kllama.sunmoon.ui.main.MainViewModel
import com.kllama.sunmoon.ui.shuttle.train.ShuttleTrainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(vm: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShuttleTrainViewModel::class)
    abstract fun bindShuttleTrainViewModel(vm: ShuttleTrainViewModel): ViewModel
}