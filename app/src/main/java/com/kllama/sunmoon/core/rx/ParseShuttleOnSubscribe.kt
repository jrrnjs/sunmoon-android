package com.kllama.sunmoon.core.rx

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kllama.sunmoon.core.parser.ShuttleParser
import com.kllama.sunmoon.models.ShuttleType
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe

class ParseShuttleOnSubscribe(private val shuttleType: ShuttleType) : CompletableOnSubscribe {

    override fun subscribe(emitter: CompletableEmitter) {
        try {
            val shuttle = ShuttleParser().parse(shuttleType)

            val ref = FirebaseDatabase.getInstance()
                    .getReference("shuttle")
                    .child(shuttleType.toString())

            ref.child("lastUpdateTime")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            if (!emitter.isDisposed) {
                                emitter.onError(p0.toException())
                            }
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val lastUpdateTime = p0.value as? Long

                            //마지막 업데이트 시간 체크

                            //마지막 데이터가 없을 경우 파싱
                            if (lastUpdateTime == null) {
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
                            } else {
                                val period = System.currentTimeMillis() - lastUpdateTime
                                //마지막 업데이트 시간이 한시간이 넘은 경우
                                if (period > 1000 * 60 * 60) {
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
                                } else {
                                    if (!emitter.isDisposed) {
                                        emitter.onError(IllegalStateException("PARSE_ERROR", Exception("Updated ${period / 1000 / 60} minutes ago.")))
                                    }
                                }
                            }

                        }
                    })


        } catch (e: Exception) {
            if (!emitter.isDisposed) {
                emitter.onError(e)
            }
        }
    }
}