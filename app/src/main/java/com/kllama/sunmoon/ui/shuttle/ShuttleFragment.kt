package com.kllama.sunmoon.ui.shuttle

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.cardview.widget.CardView
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseFragment
import com.kllama.sunmoon.extensions.*
import com.kllama.sunmoon.models.Shuttle
import com.kllama.sunmoon.models.ShuttleType
import kotlinx.android.synthetic.main.fragment_shuttle.*
import timber.log.Timber


class ShuttleFragment : BaseFragment() {

    lateinit var viewModel: ShuttleViewModel

    private var shuttleType: ShuttleType = ShuttleType.TRAIN_WEEKDAY
    private val shuttleAdapter: ShuttleAdapter = ShuttleAdapter()

    private val shuttleHeaders: Array<CardView> by lazy {
        arrayOf(
                shuttle_cardview_train_weekday_header,
                shuttle_cardview_train_weekend_header,
                shuttle_cardview_terminal_weekday_header,
                shuttle_cardview_terminal_weekend_header,
                shuttle_cardview_onyang_weekday_header
        )
    }

    override fun layoutId(): Int = R.layout.fragment_shuttle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appComponent.inject(this)

        shuttle_train_recyclerview_timetable.adapter = shuttleAdapter
        setHasOptionsMenu(true)


        viewModel = viewModel(viewModelFactory) {
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, shuttleObservable(), ::newShuttle)
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, parseResultObservable(), ::successOnParsing)
            observeOnUiThread(this@ShuttleFragment.compositeDisposable, throwable, ::handleError)
        }

        setShuttleType(ShuttleType.TRAIN_WEEKDAY)
    }

    private fun newShuttle(newItems: List<Shuttle>) {
        shuttleAdapter.newItems(newItems)
    }

    private fun successOnParsing(any: Any) {
        snackbar("셔틀시간표가 갱신되었습니다.")
    }


    fun setShuttleType(type: ShuttleType) {
        shuttleType = type
        viewModel.readShuttle(type)
        setToolbarTitle()
        setShuttleHeader()
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
    fun setShuttleHeader() {
        shuttleHeaders.forEach { it.visible(false) }
        when (shuttleType) {
            ShuttleType.TRAIN_WEEKDAY -> shuttle_cardview_train_weekday_header.visible(true)
            ShuttleType.TRAIN_SATURDAY, ShuttleType.TRAIN_SUNDAY -> shuttle_cardview_train_weekend_header.visible(true)
            ShuttleType.TERMINAL_WEEKDAY -> shuttle_cardview_terminal_weekday_header.visible(true)
            ShuttleType.TERMINAL_SATURDAY, ShuttleType.TERMINAL_SUNDAY -> shuttle_cardview_terminal_weekend_header.visible(true)
            ShuttleType.ONYANG_WEEKDAY -> shuttle_cardview_onyang_weekday_header.visible(true)
        }
    }

    private fun handleError(throwable: Throwable) {
        when (throwable.message) {
            "PARSE_ERROR" -> snackbar(throwable.cause?.message ?: "최근에 업데이트 되었습니다.")
        }
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
