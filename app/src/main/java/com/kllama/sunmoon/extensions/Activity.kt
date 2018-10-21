package com.kllama.sunmoon.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kllama.sunmoon.core.platform.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.Subject

inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory, apply: T.() -> Unit): T =
        ViewModelProviders.of(this, factory)[T::class.java].apply { this.apply() }

fun <T> BaseActivity.observeOnUiThread(compositeDisposable: CompositeDisposable, subject: Subject<T>, handle: (T) -> Unit) {
    subject.subscribeOn(io.reactivex.schedulers.Schedulers.from(executor))
            .observeOn(uiThread.scheduler)
            .subscribe(handle)
            .addTo(compositeDisposable)
}

fun BaseActivity.toast(message: String, length: Int = android.widget.Toast.LENGTH_SHORT) {
    android.widget.Toast.makeText(this, message, length).show()
}

fun BaseActivity.snackbar(message: String, length: Int = com.google.android.material.snackbar.Snackbar.LENGTH_SHORT) {
    com.google.android.material.snackbar.Snackbar.make(content, message, length).show()
}


fun BaseActivity.hideKeyboard() {
    currentFocus?.let {
        val im = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

val BaseActivity.content: View get() = findViewById(android.R.id.content)
