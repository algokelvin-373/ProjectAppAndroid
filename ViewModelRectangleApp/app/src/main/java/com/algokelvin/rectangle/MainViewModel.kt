package com.algokelvin.rectangle

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var result = 0

    fun getResults(): Int = result

    fun calculate(width: String, height: String) {
        result = width.toInt() * height.toInt()
    }
}