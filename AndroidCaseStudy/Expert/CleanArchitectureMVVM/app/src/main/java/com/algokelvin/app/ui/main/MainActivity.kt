package com.algokelvin.app.ui.main

import android.os.Bundle
import com.algokelvin.app.R
import com.algokelvin.app.base.BaseActivity
import com.algokelvin.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}