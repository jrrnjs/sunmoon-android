package com.kllama.sunmoon.core.rx

import com.google.firebase.database.*
import com.kllama.sunmoon.models.*
import io.reactivex.Observable
import io.reactivex.Observer

class ReadShuttleObservable(private val shuttleType: ShuttleType) : Observable<List<Shuttle>>() {

    private val ref: DatabaseReference = FirebaseDatabase.getInstance()
            .getReference("shuttle")
            .child(shuttleType.toString())
            .child("timeTable")

    override fun subscribeActual(observer: Observer<in List<Shuttle>>?) {
        observer?.let {
            val listener = Listener(shuttleType, ref, observer)
            observer.onSubscribe(listener)
            ref.addValueEventListener(listener)
        } ?: throw IllegalStateException("Observer can't be null!!")
    }

    private class Listener(private val shuttleType: ShuttleType,
                           private val ref: DatabaseReference,
                           private val observer: Observer<in List<Shuttle>>) : CustomDisposable(), ValueEventListener {

        override fun onDispose() {
            ref.removeEventListener(this)
        }

        override fun onCancelled(p0: DatabaseError) {
            if (!isDisposed) {
                observer.onError(p0.toException())
            }
        }

        override fun onDataChange(p0: DataSnapshot) {
            val list = when (shuttleType) {
                ShuttleType.TRAIN_WEEKDAY -> p0.children.mapNotNull { it.getValue(ShuttleTrainWeekday::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
                ShuttleType.TRAIN_SATURDAY, ShuttleType.TRAIN_SUNDAY -> p0.children.mapNotNull { it.getValue(ShuttleTrainWeekend::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
                ShuttleType.TERMINAL_WEEKDAY -> p0.children.mapNotNull { it.getValue(ShuttleTerminalWeekday::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
                ShuttleType.TERMINAL_SATURDAY, ShuttleType.TERMINAL_SUNDAY -> p0.children.mapNotNull { it.getValue(ShuttleTerminalWeekend::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
                ShuttleType.ONYANG_WEEKDAY -> p0.children.mapNotNull { it.getValue(ShuttleOnyang::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
            }

            observer.onNext(list)
        }
    }

}