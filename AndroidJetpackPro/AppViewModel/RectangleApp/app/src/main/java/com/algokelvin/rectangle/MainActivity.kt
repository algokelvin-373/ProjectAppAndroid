package com.algokelvin.rectangle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.rectangle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        activityMainBinding.btnCalculate.setOnClickListener {
            val width = activityMainBinding.edtWidth.text.toString()
            val height = activityMainBinding.edtHeight.text.toString()

            when {
                width.isEmpty() -> activityMainBinding.edtWidth.error = "The width must be filled"
                height.isEmpty() -> activityMainBinding.edtHeight.error = "The height must be filled"
                else -> calculateRectangle(width, height)
            }
        }
    }

    private fun calculateRectangle(width: String, height: String) {
        mainViewModel.calculate(width, height)
        displayResult()
    }
    private fun displayResult() {
        activityMainBinding.tvResult.text = mainViewModel.getResults().toString()
    }
}