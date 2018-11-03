package com.kllama.sunmoon.ui.shuttle

import com.kllama.sunmoon.core.platform.BaseViewModel
import com.kllama.sunmoon.models.Shuttle
import com.kllama.sunmoon.models.ShuttleTrainWeekday
import com.kllama.sunmoon.models.ShuttleType
import com.kllama.sunmoon.repository.ShuttleRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ShuttleViewModel @Inject constructor(
        private val shuttleRepository: ShuttleRepository) : BaseViewModel() {

    private var prevShuttleDisposable: Disposable? = null

    private val shuttleSubject: PublishSubject<List<Shuttle>> = PublishSubject.create()
    private val parseResultSubject: PublishSubject<Any> = PublishSubject.create()

    fun readShuttle(type: ShuttleType) {
        prevShuttleDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        prevShuttleDisposable = shuttleRepository.readShuttle(type)
                .subscribeOn(Schedulers.io())
                .subscribe(shuttleSubject::onNext, throwable::onNext)
    }

    fun parseShuttle(type: ShuttleType) {
        shuttleRepository.parseShuttle(type)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    parseResultSubject.onNext(Any())
                }) {
                    throwable.onNext(it)
                }.addTo(compositeDisposable)
    }

    fun shuttleObservable(): Observable<List<Shuttle>> = shuttleSubject
    fun parseResultObservable(): Observable<Any> = parseResultSubject

    override fun onCleared() {
        super.onCleared()
        prevShuttleDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        prevShuttleDisposable = null
    }
}