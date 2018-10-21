package com.kllama.sunmoon.extensions

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kllama.sunmoon.core.platform.BaseActivity
import com.kllama.sunmoon.core.platform.BaseFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, apply: T.() -> Unit): T =
        ViewModelProviders.of(this, factory)[T::class.java].apply { this.apply() }

fun <T> BaseFragment.observeOnUiThread(compositeDisposable: CompositeDisposable, subject: Observable<T>, handle: (T) -> Unit) {
    subject.subscribeOn(io.reactivex.schedulers.Schedulers.from(executor))
            .observeOn(uiThread.scheduler)
            .subscribe(handle)
            .addTo(compositeDisposable)
}


fun BaseFragment.close() {
    fragmentManager?.popBackStack()
}

val BaseFragment.baseActivity: BaseActivity get() = (activity as BaseActivity)

val BaseFragment.content: View get() = baseActivity.content

val BaseFragment.applicationContext: Context get() = baseActivity.applicationContext



fun BaseFragment.hideKeyboard() {
    (activity as BaseActivity).hideKeyboard()
}

fun BaseFragment.toast(message: String, length: Int = android.widget.Toast.LENGTH_SHORT) {
    android.widget.Toast.makeText(applicationContext, message, length).show()
}

fun BaseFragment.snackbar(message: String, length: Int = com.google.android.material.snackbar.Snackbar.LENGTH_SHORT) {
    com.google.android.material.snackbar.Snackbar.make(content, message, length).show()
}
