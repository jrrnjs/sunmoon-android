package com.kllama.sunmoon.ui.main

import android.os.Bundle
import com.kllama.sunmoon.R
import com.kllama.sunmoon.core.platform.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }
}
