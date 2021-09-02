package com.algokelvin.keyboard.simulator

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KeyboardController() {
    private var txtOne = ""
    private var txtTwo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setControllerInputData(R.id.first_name)
        setImgCrossInput(R.id.cross_first_name)
        setControllerKeyboard()
        setKeyboardOnClickListener()

        cl_first_name.setOnClickListener {
            txtTwo = txtInputData.text.toString()
            setActionTextKeyboardSimulator(
                R.drawable.bg_menu_white,
                R.drawable.bg_menu_grey,
                View.GONE, View.VISIBLE,
                R.id.first_name,
                R.id.cross_first_name
            )
            setTextBefore(txtOne)
        }
        cl_second_name.setOnClickListener {
            txtOne = txtInputData.text.toString()
            setActionTextKeyboardSimulator(
                R.drawable.bg_menu_grey,
                R.drawable.bg_menu_white,
                View.VISIBLE, View.GONE,
                R.id.second_name,
                R.id.cross_second_name
            )
            setTextBefore(txtTwo)
        }

    }

    private fun setActionTextKeyboardSimulator(bg1: Int, bg2: Int, v1: Int, v2: Int, txt: Int, cross: Int) {
        cl_second_name.setBackgroundResource(bg1)
        cl_first_name.setBackgroundResource(bg2)
        cross_second_name.visibility = v1
        cross_first_name.visibility = v2
        setControllerInputData(txt)
        setImgCrossInput(cross)
    }

}