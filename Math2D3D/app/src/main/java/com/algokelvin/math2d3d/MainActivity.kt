package com.algokelvin.math2d3d

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.math2d3d.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate2d.setOnClickListener {
            val strLength = binding.edtLength2d.text.toString()
            val strWidth = binding.edtWidth2d.text.toString()
            val length = strLength.toIntOrNull() ?: 0
            val width = strWidth.toIntOrNull() ?: 0
            calculate2D(length, width)
        }

        binding.btnCalculate3d.setOnClickListener {
            val strLength = binding.edtLength3d.text.toString()
            val strWidth = binding.edtWidth3d.text.toString()
            val strHeight = binding.edtHeight3d.text.toString()
            val length = strLength.toIntOrNull() ?: 0
            val width = strWidth.toIntOrNull() ?: 0
            val height = strHeight.toIntOrNull() ?: 0
            calculate3D(length, width, height)
        }
    }

    private fun calculate2D(length: Int, width: Int) {
        val result = length * width
        binding.txtResult2d.text = "The Area of Rectangle: "+ result.toString()
    }

    private fun calculate3D(length: Int, width: Int, height: Int) {
        val result = length * width * height
        binding.txtResult3d.text = "The Volume of Rectangular Prism: "+ result.toString()
    }
}