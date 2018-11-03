package com.kllama.sunmoon.ui.shuttle

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseFragment
import com.kllama.sunmoon.extensions.baseActivity
import com.kllama.sunmoon.extensions.observeOnUiThread
import com.kllama.sunmoon.extensions.snackbar
import com.kllama.sunmoon.extensions.viewModel
import com.kllama.sunmoon.models.Shuttle
import com.kllama.sunmoon.models.ShuttleType
import kotlinx.android.synthetic.main.fragment_shuttle_train.*


class ShuttleFragment : BaseFragment() {

    lateinit var viewModel: ShuttleViewModel

    private var shuttleType: ShuttleType = ShuttleType.TRAIN_WEEKDAY
    private val shuttleAdapter: ShuttleAdapter = ShuttleAdapter()

    override fun layoutId(): Int = R.layout.fragment_shuttle_train

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appComponent.inject(this)

        shuttle_train_recyclerview_timetable.adapter = shuttleAdapter
        setToolbarTitle()
        setHasOptionsMenu(true)


        viewModel = viewModel(viewModelFactory) {
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, shuttleObservable(), ::newShuttle)
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, parseResultObservable(), ::successOnParsing)
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, throwable, Throwable::printStackTrace)
        }

        viewModel.readShuttle(shuttleType)
    }

    private fun newShuttle(newItems: List<Shuttle>) {
        shuttleAdapter.newItems(newItems)
    }

    private fun successOnParsing(any: Any) {
        snackbar("셔틀시간표가 갱신되었습니다.")
    }

    private fun setToolbarTitle() {
        baseActivity.supportActionBar?.title = when (shuttleType) {
            ShuttleType.TRAIN_WEEKDAY -> "기차역 평일"
            ShuttleType.TRAIN_SATURDAY -> "기차역 토요일"
            ShuttleType.TRAIN_SUNDAY -> "기차역 일요일"
            ShuttleType.TERMINAL_WEEKDAY -> "터미널 평일"
            ShuttleType.TERMINAL_SATURDAY -> "터미널 토요일"
            ShuttleType.TERMINAL_SUNDAY -> "터미널 일요일"
            ShuttleType.ONYANG_WEEKDAY -> "온양 평일"
        }
    }

    fun setShuttleType(type: ShuttleType) {
        shuttleType = type
        viewModel.readShuttle(type)
        setToolbarTitle()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_shuttle, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_parse -> {
                viewModel.parseShuttle(shuttleType)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "ShuttleFragment"

        @JvmStatic
        fun newInstance() = ShuttleFragment().apply {
            arguments = Bundle()
        }
    }
}
