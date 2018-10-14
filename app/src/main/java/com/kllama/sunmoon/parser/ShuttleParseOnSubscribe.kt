package com.kllama.sunmoon.parser

import com.google.firebase.database.FirebaseDatabase
import com.kllama.sunmoon.models.ShuttleType
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe

class ShuttleParseOnSubscribe(private val shuttleType: ShuttleType) : CompletableOnSubscribe {

    override fun subscribe(emitter: CompletableEmitter) {
        try {
            val shuttle = ShuttleParser().parse(shuttleType)
            val ref = FirebaseDatabase.getInstance()
                    .getReference("shuttle")
                    .child(shuttleType.toString())

            ref.child("timeTable")
                    .setValue(shuttle)
                    .addOnCompleteListener {
                        if (!emitter.isDisposed) {
                            if (it.isSuccessful) {
                                ref.child("lastUpdateTime")
                                        .setValue(System.currentTimeMillis())
                                emitter.onComplete()
                            } else {
                                it.exception?.let { emitter.onError(it) }
                            }
                        }
                    }

        } catch (e: Exception) {
            if (!emitter.isDisposed) {
                emitter.onError(e)
            }
        }
    }
}