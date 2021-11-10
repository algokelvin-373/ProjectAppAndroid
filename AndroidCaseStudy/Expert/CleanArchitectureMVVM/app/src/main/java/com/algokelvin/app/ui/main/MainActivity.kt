package com.algokelvin.app.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.app.base.BaseActivity
import com.algokelvin.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModelFactory by lazy {
        MainViewModelFactory(applicationContext)
    }

    private val mainViewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}