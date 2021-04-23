package com.algokelvin.animationandroid

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation = ObjectAnimator.ofFloat(txt_hello, "translationX", 200f)
        animation.duration = 2000
        animation.start()
    }
}