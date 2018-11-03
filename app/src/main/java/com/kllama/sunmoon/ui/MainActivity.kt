package com.kllama.sunmoon.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseActivity
import com.kllama.sunmoon.extensions.inTransaction
import com.kllama.sunmoon.extensions.onClick
import com.kllama.sunmoon.models.ShuttleType
import com.kllama.sunmoon.ui.shuttle.ShuttleFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_backdrop.*

class MainActivity : BaseActivity(), NavigationHost {

    lateinit var navigationIconClickListener: NavigationClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        setUpToolbar(main_frame_container)

        initBackdropButtons()

        supportFragmentManager.inTransaction {
            add(R.id.main_frame_container, ShuttleFragment(), ShuttleFragment.TAG)
        }
    }

    private fun getShuttleFragment(): ShuttleFragment? =
            supportFragmentManager.findFragmentByTag(ShuttleFragment.TAG) as? ShuttleFragment

    private fun setUpToolbar(view: View) {
        navigationIconClickListener = NavigationClickListener(
                this,
                view,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(this, R.drawable.ic_menu_24dp),
                ContextCompat.getDrawable(this, R.drawable.ic_close_menu_24dp)
        )

        main_toolbar.setNavigationOnClickListener(navigationIconClickListener)
    }

    private fun initBackdropButtons() {
        main_button_train_weekday onClick ::onBackdropButtonClick
        main_button_train_saturday onClick ::onBackdropButtonClick
        main_button_train_sunday onClick ::onBackdropButtonClick
        main_button_cterminal_weekday onClick ::onBackdropButtonClick
        main_button_cterminal_saturday onClick ::onBackdropButtonClick
        main_button_cterminal_sunday onClick ::onBackdropButtonClick
        main_button_yterminal onClick ::onBackdropButtonClick
    }

    private fun onBackdropButtonClick(view: View) {
        when (view.id) {
            R.id.main_button_train_weekday -> getShuttleFragment()?.setShuttleType(ShuttleType.TRAIN_WEEKDAY)
            R.id.main_button_train_saturday -> getShuttleFragment()?.setShuttleType(ShuttleType.TRAIN_SATURDAY)
            R.id.main_button_train_sunday -> getShuttleFragment()?.setShuttleType(ShuttleType.TRAIN_SUNDAY)
            R.id.main_button_cterminal_weekday -> getShuttleFragment()?.setShuttleType(ShuttleType.TERMINAL_WEEKDAY)
            R.id.main_button_cterminal_saturday -> getShuttleFragment()?.setShuttleType(ShuttleType.TERMINAL_SATURDAY)
            R.id.main_button_cterminal_sunday -> getShuttleFragment()?.setShuttleType(ShuttleType.TERMINAL_SUNDAY)
            R.id.main_button_yterminal -> getShuttleFragment()?.setShuttleType(ShuttleType.ONYANG_WEEKDAY)
            else -> return
        }

        navigationIconClickListener.menuClose()
    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
