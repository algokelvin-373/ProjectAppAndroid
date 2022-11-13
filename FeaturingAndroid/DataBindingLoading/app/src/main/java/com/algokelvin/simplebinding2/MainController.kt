package com.algokelvin.simplebinding2

import com.algokelvin.simplebinding2.Utils.setVisibleProgressbar
import com.algokelvin.simplebinding2.databinding.ActivityMainBinding

class MainController(bind: ActivityMainBinding) {
    private var binding: ActivityMainBinding = bind

    fun startOrStopProgressBar() {
        binding.apply {
            if (progressBar.visibility == Utils.VIEW_GONE) {
                setVisibleProgressbar(Utils.VIEW_VISIBLE, "Stop")
            } else {
                setVisibleProgressbar(Utils.VIEW_GONE, "Start")
            }
        }
    }

}