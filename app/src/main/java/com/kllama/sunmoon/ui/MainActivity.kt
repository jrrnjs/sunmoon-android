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
import com.kllama.sunmoon.extensions.toast
import com.kllama.sunmoon.ui.shuttle.train.ShuttleTrainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_backdrop.*

class MainActivity : BaseActivity(), NavigationHost {

    lateinit var navigationIconClickListener: NavigationIconClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)
        setUpToolbar(main_frame_container)

        initBackdropButtons()

        supportFragmentManager.inTransaction {
            add(R.id.main_frame_container, ShuttleTrainFragment(), ShuttleTrainFragment.TAG)
        }
    }

    private fun onBackdropButtonClick(view: View) {
        when (view.id) {
            R.id.main_button_train_weekday -> toast("기차평일")
            R.id.main_button_train_saturday -> toast("기차토요일")
            R.id.main_button_train_sunday -> toast("기차일요일")
            R.id.main_button_cterminal_weekday -> toast("터미널평일")
            R.id.main_button_cterminal_saturday -> toast("터미널토요일")
            R.id.main_button_cterminal_sunday -> toast("터미널일요일")
            R.id.main_button_yterminal -> toast("온양")
            else -> return
        }

        navigationIconClickListener.menuClose()
    }

    private fun setToolbarTitle(title: String) {
        main_toolbar.title = title
    }


    private fun setUpToolbar(view: View) {
        navigationIconClickListener = NavigationIconClickListener(
                this,
                view,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(this, R.drawable.ic_menu_24dp),
                ContextCompat.getDrawable(this, R.drawable.ic_close_menu_24dp)
        )

        main_toolbar.setNavigationOnClickListener(navigationIconClickListener)
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

    private fun initBackdropButtons() {
        main_button_train_weekday onClick ::onBackdropButtonClick
        main_button_train_saturday onClick ::onBackdropButtonClick
        main_button_train_sunday onClick ::onBackdropButtonClick
        main_button_cterminal_weekday onClick ::onBackdropButtonClick
        main_button_cterminal_saturday onClick ::onBackdropButtonClick
        main_button_cterminal_sunday onClick ::onBackdropButtonClick
        main_button_yterminal onClick ::onBackdropButtonClick
    }

}
