package com.algokelvin.animationandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.animationandroid.translation.TranslationOne
import com.algokelvin.animationandroid.translation.TranslationTwo
import kotlinx.android.synthetic.main.activity_menu_translation.*

class MenuTranslation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_translation)

        translation01.setOnClickListener {
            startActivity(Intent(this, TranslationOne::class.java))
        }
        translation02.setOnClickListener {
            startActivity(Intent(this, TranslationTwo::class.java))
        }
    }
}