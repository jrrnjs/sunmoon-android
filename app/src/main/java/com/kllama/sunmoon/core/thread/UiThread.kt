package com.kllama.sunmoon.core.thread

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UiThread @Inject constructor() {
    val scheduler: Scheduler get() = AndroidSchedulers.mainThread()
}