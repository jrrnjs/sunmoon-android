package com.kllama.sunmoon.ui.shuttle.train

import com.kllama.sunmoon.core.platform.BaseViewModel
import com.kllama.sunmoon.models.ShuttleTrain
import com.kllama.sunmoon.models.ShuttleType
import com.kllama.sunmoon.repository.ShuttleRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ShuttleTrainViewModel @Inject constructor(
        private val shuttleRepository: ShuttleRepository) : BaseViewModel() {

    private var prevShuttleDisposable: Disposable? = null

    private val shuttleTrainSubject: PublishSubject<List<ShuttleTrain>> = PublishSubject.create()

    fun readShuttle(shuttleType: ShuttleType) {
        val observable = when (shuttleType) {
            ShuttleType.TRAIN_WEEKDAY -> shuttleRepository.trainWeekday()
            ShuttleType.TRAIN_SATURDAY -> shuttleRepository.trainSaturday()
            ShuttleType.TRAIN_SUNDAY -> shuttleRepository.trainSunday()
            else -> return
        }

        prevShuttleDisposable?.dispose()
        prevShuttleDisposable = observable
                .subscribe(shuttleTrainSubject::onNext, throwable::onNext)
                .addTo(compositeDisposable)
    }

    fun shuttleTrainObservable(): Observable<List<ShuttleTrain>> = shuttleTrainSubject

}