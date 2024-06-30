package com.algokelvin.conversionsimple.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.conversionsimple.base.function.ConversionFunction
import com.algokelvin.conversionsimple.databinding.ActivityConversionBinding

class ConversionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConversionBinding
    private lateinit var function: ConversionFunction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversionBinding.inflate(layoutInflater)
        function = ConversionFunction(this)
        setContentView(binding.root)


    }
}