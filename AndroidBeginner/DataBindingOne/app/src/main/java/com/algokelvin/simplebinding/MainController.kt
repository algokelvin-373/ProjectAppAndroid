package com.algokelvin.simplebinding

import com.algokelvin.simplebinding.databinding.ActivityMainBinding

class MainController(bind: ActivityMainBinding) {

    private var binding: ActivityMainBinding = bind
    private var name: String = "Kelvin"

    fun getName() = name

    fun setGreetings() {
        binding.greetingTextView.text = ("Hello! "+ binding.nameEditText.text)
    }

}