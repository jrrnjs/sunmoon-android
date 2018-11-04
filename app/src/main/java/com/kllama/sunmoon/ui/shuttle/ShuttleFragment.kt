package com.kllama.sunmoon.ui.shuttle

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.SettingsPreferences
import com.kllama.sunmoon.core.platform.BaseFragment
import com.kllama.sunmoon.extensions.*
import com.kllama.sunmoon.models.Shuttle
import com.kllama.sunmoon.models.ShuttleType
import kotlinx.android.synthetic.main.fragment_shuttle.*
import javax.inject.Inject


class ShuttleFragment : BaseFragment() {

    @Inject
    lateinit var settingsPreferences: SettingsPreferences
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

        setShuttleType(settingsPreferences.getDefaultPage())
    }

    private fun newShuttle(newItems: List<Shuttle>) {
        shuttleAdapter.newItems(newItems)
    }

    private fun successOnParsing(any: Any) {
        snackbar(getString(R.string.shuttle_schedule_has_been_updated))
    }


    fun setShuttleType(type: ShuttleType) {
        shuttleType = type
        viewModel.readShuttle(type)
        setToolbarTitle()
        setShuttleHeader()
    }

    private fun setToolbarTitle() {
        baseActivity.supportActionBar?.title = when (shuttleType) {
            ShuttleType.TRAIN_WEEKDAY -> getString(R.string.train_weekday)
            ShuttleType.TRAIN_SATURDAY -> getString(R.string.train_saturday)
            ShuttleType.TRAIN_SUNDAY -> getString(R.string.train_sunday)
            ShuttleType.TERMINAL_WEEKDAY -> getString(R.string.c_terminal_weekday)
            ShuttleType.TERMINAL_SATURDAY -> getString(R.string.c_terminal_saturday)
            ShuttleType.TERMINAL_SUNDAY -> getString(R.string.c_terminal_sunday)
            ShuttleType.ONYANG_WEEKDAY -> getString(R.string.o_terminal)
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
            "PARSE_ERROR" -> snackbar(throwable.cause?.message ?: getString(R.string.recently_updated))
        }
    }


    private fun showDefaultSettingDialog() {
        val items = arrayOf(
                getString(R.string.train_weekday), getString(R.string.train_saturday), getString(R.string.train_sunday),
                getString(R.string.c_terminal_weekday), getString(R.string.c_terminal_saturday), getString(R.string.c_terminal_sunday),
                getString(R.string.o_terminal)
        )
        var selectedValue = settingsPreferences.getDefaultPage().ordinal

        AlertDialog.Builder(baseActivity)
                .setTitle(R.string.select_default_page)
                .setSingleChoiceItems(items, selectedValue) {dialog, which ->
                    selectedValue = which
                }.setPositiveButton(R.string.save) { dialog, which ->
                    settingsPreferences.setDefaultPage(ShuttleType.values()[selectedValue])
                }.create().show()
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
            R.id.menu_settings -> {
                showDefaultSettingDialog()
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
