package com.algokelvin.keyboard.simulator

import android.os.Bundle

class MainActivity : KeyboardController() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setControllerInputData(R.id.first_name)
        setImgCrossInput(R.id.cross_first_name)
        setControllerKeyboard()
        setKeyboardOnClickListener()

    }
}