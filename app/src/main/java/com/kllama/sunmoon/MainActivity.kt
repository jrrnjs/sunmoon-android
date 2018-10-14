package com.kllama.sunmoon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kllama.sunmoon.models.ShuttleType
import com.kllama.sunmoon.parser.ShuttleParseOnSubscribe
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.WEEKDAY_TRAIN))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.SUNDAY_TRAIN))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.WEEKEND_TRAIN))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)


        Completable.create(ShuttleParseOnSubscribe(ShuttleType.WEEKDAY_TERMINAL))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.SUNDAY_TERMINAL))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.WEEKEND_TERMINAL))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)

        Completable.create(ShuttleParseOnSubscribe(ShuttleType.WEEKDAY_ONYANG))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)



    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
