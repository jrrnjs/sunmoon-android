package com.kllama.sunmoon.repository

import com.kllama.sunmoon.core.rx.ParseShuttleOnSubscribe
import com.kllama.sunmoon.core.rx.ReadShuttleObservable
import com.kllama.sunmoon.models.Shuttle
import com.kllama.sunmoon.models.ShuttleType
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

interface ShuttleRepository {

    fun readShuttle(type: ShuttleType): Observable<List<Shuttle>>
    fun parseShuttle(type: ShuttleType): Completable

    class Impl @Inject constructor() : ShuttleRepository {

        override fun readShuttle(type: ShuttleType): Observable<List<Shuttle>> = ReadShuttleObservable(type)
        override fun parseShuttle(type: ShuttleType): Completable = Completable.create(ParseShuttleOnSubscribe(type))
    }
}