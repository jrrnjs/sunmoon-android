package com.kllama.sunmoon.ui.main

import android.os.Bundle
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseActivity
import com.kllama.sunmoon.core.rx.ShuttleParseOnSubscribe
import com.kllama.sunmoon.extensions.inTransaction
import com.kllama.sunmoon.extensions.onClick
import com.kllama.sunmoon.extensions.viewModel
import com.kllama.sunmoon.models.ShuttleType
import com.kllama.sunmoon.ui.shuttle.train.ShuttleTrainFragment
import io.reactivex.Completable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        main_fab_reload onClick ::reload

        viewModel = viewModel(viewModelFactory) {

        }



        supportFragmentManager.inTransaction {
            add(R.id.main_frame_container, ShuttleTrainFragment(), ShuttleTrainFragment.TAG)
        }
    }

    private fun reload() {
        Completable.create(ShuttleParseOnSubscribe(ShuttleType.TRAIN_WEEKDAY))
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addTo(compositeDisposable)
    }
}
