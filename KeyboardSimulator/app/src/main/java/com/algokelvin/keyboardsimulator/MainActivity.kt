package com.algokelvin.keyboardsimulator

import android.os.Bundle
import android.view.View
import com.algokelvin.keyboardsimulator.databinding.ActivityMainBinding

class MainActivity : KeyboardController() {
    private lateinit var binding: ActivityMainBinding
    private var txtOne = ""
    private var txtTwo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setControllerInputData(R.id.first_name)
//        setImgCrossInput(R.id.cross_first_name)
//        setControllerKeyboard()
//        setKeyboardOnClickListener()

        binding.clFirstName.setOnClickListener {
            txtTwo = txtInputData.text.toString()
            setActionTextKeyboardSimulator(
                R.drawable.bg_menu_white,
                R.drawable.bg_menu_grey,
                View.GONE, View.VISIBLE,
                R.id.first_name,
                R.id.cross_first_name
            )
        }
        binding.clSecondName.setOnClickListener {
            txtOne = txtInputData.text.toString()
            setActionTextKeyboardSimulator(
                R.drawable.bg_menu_grey,
                R.drawable.bg_menu_white,
                View.VISIBLE, View.GONE,
                R.id.second_name,
                R.id.cross_second_name
            )
        }

    }

    private fun setActionTextKeyboardSimulator(bg1: Int, bg2: Int, v1: Int, v2: Int, txt: Int, cross: Int) {
        binding.clSecondName.setBackgroundResource(bg1)
        binding.clFirstName.setBackgroundResource(bg2)
        binding.crossSecondName.visibility = v1
        binding.crossFirstName.visibility = v2
//        setControllerInputData(txt)
//        setImgCrossInput(cross)
    }

}