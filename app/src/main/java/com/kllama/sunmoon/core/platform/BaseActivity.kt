package com.kllama.sunmoon.core.platform

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.ViewModelProvider
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.thread.ThreadExecutor
import com.kllama.sunmoon.core.thread.UiThread
import com.kllama.sunmoon.SMApplication
import com.kllama.sunmoon.core.di.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {


    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as SMApplication).appComponent
    }

    lateinit var compositeDisposable: CompositeDisposable


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var uiThread: UiThread
    @Inject
    lateinit var executor: ThreadExecutor


    private val progress: AppCompatDialog by lazy {
        AppCompatDialog(this@BaseActivity).apply {
            setCancelable(false)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.layout_progress)
        }
    }


    private fun progress(show: Boolean) {
        if (show && !progress.isShowing) {
            progress.show()
        } else if (!show && progress.isShowing) {
            progress.dismiss()
        }
    }

    fun showProgress() = progress(true)

    fun hideProgress() = progress(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}