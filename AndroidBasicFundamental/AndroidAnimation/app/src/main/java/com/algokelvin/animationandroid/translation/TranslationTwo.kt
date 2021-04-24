package com.algokelvin.animationandroid.translation

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.algokelvin.animationandroid.R
import kotlinx.android.synthetic.main.activity_translation_one.*

class TranslationTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_one)

        val animation = ObjectAnimator.ofFloat(txt_hello, "translationX", -200f)
        animation.duration = 1000
        animation.start()
    }
}