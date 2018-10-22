package com.kllama.sunmoon.ui.shuttle.train

import android.os.Bundle
import android.view.View
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseFragment
import com.kllama.sunmoon.extensions.observeOnUiThread
import com.kllama.sunmoon.extensions.viewModel
import com.kllama.sunmoon.models.ShuttleTrain
import com.kllama.sunmoon.models.ShuttleTrainWeekday
import com.kllama.sunmoon.models.ShuttleType
import kotlinx.android.synthetic.main.fragment_shuttle_train.*
import timber.log.Timber


class ShuttleTrainFragment : BaseFragment() {

    lateinit var viewModel: ShuttleTrainViewModel



    private var shuttleType: ShuttleType = ShuttleType.TRAIN_WEEKDAY


    override fun layoutId(): Int = R.layout.fragment_shuttle_train

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            observeOnUiThread(this@ShuttleTrainFragment.compositeDisposable, shuttleTrainObservable(), ::loadTimetable)
            observeOnUiThread(this@ShuttleTrainFragment.compositeDisposable, throwable, Throwable::printStackTrace)
        }

        viewModel.readShuttle(shuttleType)
    }

    private fun loadTimetable(newTimetable: List<ShuttleTrain>) {
        newTimetable.forEach {
            Timber.e(it.toString())
        }
        setSection()
    }

    private fun setSection() {
        when (shuttleType) {
            ShuttleType.TRAIN_WEEKDAY -> {
                shuttle_train_toolbar.title = "기차역/평일"
            }
            ShuttleType.TRAIN_SATURDAY -> {
                shuttle_train_toolbar.title = "기차역/토요일"
            }
            ShuttleType.TRAIN_SUNDAY -> {
                shuttle_train_toolbar.title = "기차역/일요일"
            }
            else -> return
        }
    }

    companion object {
        const val TAG = "ShuttleTrainFragment"

        @JvmStatic
        fun newInstance() = ShuttleTrainFragment().apply {
            arguments = Bundle()
        }
    }
}
