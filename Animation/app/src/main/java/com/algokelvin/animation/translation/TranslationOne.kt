package com.algokelvin.animation.translation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.animation.AnimatorController
import com.algokelvin.animation.databinding.ActivityTranslationOneBinding

class TranslationOne : AppCompatActivity() {

    private lateinit var binding: ActivityTranslationOneBinding
    private val animatorController = AnimatorController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslationOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Make TextView translate X to coordinate 200 with duration 2 seconds.
        This method use the module 'animator-featuring'
         */
        animatorController.animatorTextView(binding.txtHello, "translationX", 200f, 2000)
    }
}