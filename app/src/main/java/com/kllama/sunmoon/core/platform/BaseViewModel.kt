package com.kllama.sunmoon.core.platform

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var throwable: PublishSubject<Throwable> = PublishSubject.create()

    protected fun pushError(throwable: Throwable) {
        this.throwable.onNext(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}