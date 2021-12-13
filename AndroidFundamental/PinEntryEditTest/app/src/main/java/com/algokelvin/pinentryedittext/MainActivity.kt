package com.algokelvin.pinentryedittext

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.include_pin.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt_pin_1.addTextChangedListener(inputPinController(edt_pin_1))
        edt_pin_2.addTextChangedListener(inputPinController(edt_pin_2))
        edt_pin_3.addTextChangedListener(inputPinController(edt_pin_3))
        edt_pin_4.addTextChangedListener(inputPinController(edt_pin_4))
        edt_pin_5.addTextChangedListener(inputPinController(edt_pin_5))
        edt_pin_6.addTextChangedListener(inputPinController(edt_pin_6))
    }

    private fun inputPinController(view: View) = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val numberOne = edt_pin_1.text.toString().trim()
            val numberTwo = edt_pin_2.text.toString().trim()
            val numberThree = edt_pin_3.text.toString().trim()
            val numberFour = edt_pin_4.text.toString().trim()
            val numberFive = edt_pin_5.text.toString().trim()
            val numberSix = edt_pin_6.text.toString().trim()

            when(view.id) {
                R.id.edt_pin_1 -> {
                    if (numberOne.length == 1) {
                        edt_pin_2.requestFocus()
                    }
                }
                R.id.edt_pin_2 -> {
                    if (numberTwo.length == 1) {
                        edt_pin_3.requestFocus()
                    } else if (numberTwo.isEmpty()) {
                        edt_pin_1.requestFocus()
                    }
                }
                R.id.edt_pin_3 -> {
                    if (numberThree.length == 1) {
                        edt_pin_4.requestFocus()
                    } else if (numberThree.isEmpty()) {
                        edt_pin_2.requestFocus()
                    }
                }
                R.id.edt_pin_4 -> {
                    if (numberFour.length == 1) {
                        edt_pin_5.requestFocus()
                    } else if (numberFour.isEmpty()) {
                        edt_pin_3.requestFocus()
                    }
                }
                R.id.edt_pin_5 -> {
                    if (numberFive.length == 1) {
                        edt_pin_6.requestFocus()
                    } else if (numberFive.isEmpty()) {
                        edt_pin_4.requestFocus()
                    }
                }
                R.id.edt_pin_6 -> {
                    if (numberSix.isNotEmpty()) {
                        val number = numberOne + numberTwo + numberThree + numberFour + numberFive + numberSix
                        Toast.makeText(this@MainActivity, "Input : $number", Toast.LENGTH_LONG).show()
                    } else {
                        edt_pin_5.requestFocus()
                    }
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) { }
    }
}