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
    }
}