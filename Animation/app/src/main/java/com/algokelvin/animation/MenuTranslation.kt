package com.algokelvin.animation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.animation.databinding.ActivityMenuTranslationBinding
import com.algokelvin.animation.translation.TranslationOne
import com.algokelvin.animation.translation.TranslationTwo

class MenuTranslation : AppCompatActivity() {
    private lateinit var binding: ActivityMenuTranslationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuTranslationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.translation01.setOnClickListener {
            startActivity(Intent(this, TranslationOne::class.java))
        }
        binding.translation02.setOnClickListener {
            startActivity(Intent(this, TranslationTwo::class.java))
        }
    }
}