package com.algokelvin.simplebinding2

import android.view.View
import com.algokelvin.simplebinding2.databinding.ActivityMainBinding

object Utils {
    const val VIEW_GONE = View.GONE
    const val VIEW_VISIBLE = View.VISIBLE

    fun ActivityMainBinding.setVisibleProgressbar(statusVisible: Int, msg: String) {
        progressBar.visibility = statusVisible
        controlButton.text = msg
    }

}