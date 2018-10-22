package com.kllama.sunmoon.repository

import com.kllama.sunmoon.core.rx.ShuttleTrainObservable
import com.kllama.sunmoon.models.ShuttleTrain
import com.kllama.sunmoon.models.ShuttleType
import io.reactivex.Observable
import javax.inject.Inject

interface ShuttleRepository {

    fun trainWeekday(): Observable<List<ShuttleTrain>>
    fun trainSaturday(): Observable<List<ShuttleTrain>>
    fun trainSunday(): Observable<List<ShuttleTrain>>

    class Impl @Inject constructor() : ShuttleRepository {

        override fun trainWeekday(): Observable<List<ShuttleTrain>> = ShuttleTrainObservable(ShuttleType.TRAIN_WEEKDAY)

        override fun trainSaturday(): Observable<List<ShuttleTrain>> = ShuttleTrainObservable(ShuttleType.TRAIN_SATURDAY)

        override fun trainSunday(): Observable<List<ShuttleTrain>> = ShuttleTrainObservable(ShuttleType.TRAIN_SUNDAY)
    }
}