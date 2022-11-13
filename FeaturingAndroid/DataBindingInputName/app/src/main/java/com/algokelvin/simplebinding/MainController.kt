package com.algokelvin.simplebinding

import android.content.Context
import com.algokelvin.simplebinding.databinding.ActivityMainBinding

class MainController(private val context: Context, bind: ActivityMainBinding) {

    private var binding: ActivityMainBinding = bind
    private var name: String = "Kelvin"

    fun getName() = name

    fun setGreetings() {
        binding.greetingTextView.text = context.getString(
            R.string.hello_world,
            binding.nameEditText.text
        )
    }

}