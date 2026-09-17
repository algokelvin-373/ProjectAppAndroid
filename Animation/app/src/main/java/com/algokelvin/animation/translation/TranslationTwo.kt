package com.algokelvin.animation.translation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.animation.AnimatorController
import com.algokelvin.animation.databinding.ActivityTranslationTwoBinding

class TranslationTwo : AppCompatActivity() {
    private lateinit var binding: ActivityTranslationTwoBinding
    private val animatorController = AnimatorController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            animatorController.translateX(binding.txtHello, -200f, 1000)
        }
        binding.btnReverse.setOnClickListener {
            animatorController.translateX(binding.txtHello, 200f, 1000)
        }
        binding.btnReset.setOnClickListener {
            animatorController.reset(binding.txtHello)
        }
        binding.btnAlpha.setOnClickListener {
            animatorController.fade(binding.txtHello, 0.2f, 1000)
        }
        binding.btnRotation.setOnClickListener {
            animatorController.rotate(binding.txtHello, -360f, 1000)
        }
        binding.btnScale.setOnClickListener {
            animatorController.scale(binding.txtHello, 1.6f, 1000)
        }
    }
}
