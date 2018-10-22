package com.kllama.sunmoon.core.rx

import com.google.firebase.database.*
import com.kllama.sunmoon.models.ShuttleTrain
import com.kllama.sunmoon.models.ShuttleTrainWeekday
import com.kllama.sunmoon.models.ShuttleTrainWeekend
import com.kllama.sunmoon.models.ShuttleType
import io.reactivex.Observable
import io.reactivex.Observer

class ShuttleTrainObservable(private val shuttleType: ShuttleType) : Observable<List<ShuttleTrain>>() {

    private val ref: DatabaseReference = when (shuttleType) {
        ShuttleType.TRAIN_WEEKDAY, ShuttleType.TRAIN_SATURDAY, ShuttleType.TRAIN_SUNDAY -> {
            FirebaseDatabase.getInstance()
                    .getReference("shuttle")
                    .child(shuttleType.toString())
                    .child("timeTable")
        }
        else -> throw IllegalStateException("Values other than train type are not allowed.")
    }

    override fun subscribeActual(observer: Observer<in List<ShuttleTrain>>?) {
        observer?.let {
            val listener = Listener(shuttleType, ref, observer)
            observer.onSubscribe(listener)
            ref.addValueEventListener(listener)
        } ?: throw IllegalStateException("Observer can't be null!!")
    }

    private class Listener(private val shuttleType: ShuttleType,
                           private val ref: DatabaseReference,
                           private val observer: Observer<in List<ShuttleTrain>>) : CustomDisposable(), ValueEventListener {

        override fun onDispose() {
            ref.removeEventListener(this)
        }

        override fun onCancelled(p0: DatabaseError) {
            if (!isDisposed) {
                observer.onError(p0.toException())
            }
        }

        override fun onDataChange(p0: DataSnapshot) {
            val list = if (shuttleType == ShuttleType.TRAIN_WEEKDAY) {
                p0.children.mapNotNull { it.getValue(ShuttleTrainWeekday::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
            } else if (shuttleType == ShuttleType.TRAIN_SATURDAY || shuttleType == ShuttleType.TRAIN_SUNDAY) {
                p0.children.mapNotNull { it.getValue(ShuttleTrainWeekend::class.java) }
                        .sortedBy { it.no.toInt() }
                        .toList()
            } else null

            list?.let {
                observer.onNext(it)
            }
        }
    }

}