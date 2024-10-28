package com.algokelvin.twonumberoperations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.twonumberoperations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlus.setOnClickListener {
            var number1 = 0.0
            if (binding.edtNumber1.text.toString().isNotEmpty())
                number1 = binding.edtNumber1.text.toString().toDouble()
            var number2 = 0.0
            if (binding.edtNumber2.text.toString().isNotEmpty())
                number2 = binding.edtNumber2.text.toString().toDouble()
            val result = number1 + number2
            binding.txtResult.text = result.toString()
        }
        binding.btnMinus.setOnClickListener {
            var number1 = 0.0
            if (binding.edtNumber1.text.toString().isNotEmpty())
                number1 = binding.edtNumber1.text.toString().toDouble()
            var number2 = 0.0
            if (binding.edtNumber2.text.toString().isNotEmpty())
                number2 = binding.edtNumber2.text.toString().toDouble()
            val result = number1 - number2
            binding.txtResult.text = result.toString()
        }
    }
}