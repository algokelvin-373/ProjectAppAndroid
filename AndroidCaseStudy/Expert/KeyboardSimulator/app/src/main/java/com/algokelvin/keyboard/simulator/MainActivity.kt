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
            cl_second_name.setBackgroundResource(R.drawable.bg_menu_white)
            cl_first_name.setBackgroundResource(R.drawable.bg_menu_grey)
            cross_second_name.visibility = View.GONE
            cross_first_name.visibility = View.VISIBLE
            setControllerInputData(R.id.first_name)
            setImgCrossInput(R.id.cross_first_name)
            setTextBefore(txtOne)
        }
        cl_second_name.setOnClickListener {
            txtOne = txtInputData.text.toString()
            cl_second_name.setBackgroundResource(R.drawable.bg_menu_grey)
            cl_first_name.setBackgroundResource(R.drawable.bg_menu_white)
            cross_second_name.visibility = View.VISIBLE
            cross_first_name.visibility = View.GONE
            setControllerInputData(R.id.second_name)
            setImgCrossInput(R.id.cross_second_name)
            setTextBefore(txtTwo)
        }

    }
}