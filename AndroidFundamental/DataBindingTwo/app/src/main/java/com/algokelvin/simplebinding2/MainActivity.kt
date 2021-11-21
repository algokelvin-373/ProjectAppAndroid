package com.algokelvin.simplebinding2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.algokelvin.simplebinding2.Utils.VIEW_GONE
import com.algokelvin.simplebinding2.Utils.VIEW_VISIBLE
import com.algokelvin.simplebinding2.Utils.setVisibleProgressbar
import com.algokelvin.simplebinding2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.controlButton.setOnClickListener {
            startOrStopProgressBar()
        }
    }

    private fun startOrStopProgressBar() {
        binding.apply {
            if (progressBar.visibility == VIEW_GONE) {
                setVisibleProgressbar(VIEW_VISIBLE, "Stop")
            } else {
                setVisibleProgressbar(VIEW_GONE, "Start")
            }
        }
    }
}

