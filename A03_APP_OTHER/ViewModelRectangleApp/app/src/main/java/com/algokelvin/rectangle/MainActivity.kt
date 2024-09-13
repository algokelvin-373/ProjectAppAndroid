package com.algokelvin.rectangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.rectangle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener {
            val width = binding.edtWidth.text.toString()
            val height = binding.edtHeight.text.toString()

            when {
                width.isEmpty() -> binding.edtWidth.error = "The width must be filled"
                height.isEmpty() -> binding.edtHeight.error = "The height must be filled"
                else -> calculateRectangle(width, height)
            }
        }
    }

    private fun calculateRectangle(width: String, height: String) {
        mainViewModel.calculate(width, height)
        displayResult()
    }
    private fun displayResult() {
        binding.tvResult.text = mainViewModel.getResults().toString()
    }
}