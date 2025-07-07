package com.algokelvin.vvg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.vvg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.view)

        binding.btnLinearLayout.setOnClickListener {
            //TODO
        }

        binding.btnRelativeLayout.setOnClickListener {
            //TODO
        }

        binding.btnFrameLayout.setOnClickListener {
            //TODO
        }

        binding.btnTableLayout.setOnClickListener {
            //TODO
        }
    }
}