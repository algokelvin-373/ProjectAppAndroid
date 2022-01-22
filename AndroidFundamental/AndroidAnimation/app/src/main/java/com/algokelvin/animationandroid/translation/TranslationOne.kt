package com.algokelvin.animationandroid.translation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.animationandroid.R
import algokelvin.app.animatorfeaturing.AnimatorController.TRANSLATE_X
import kotlinx.android.synthetic.main.activity_translation_one.*

class TranslationOne : AppCompatActivity() {

    private val animatorController = algokelvin.app.animatorfeaturing.AnimatorController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation_one)

        animatorController.animatorTextView(txt_hello, TRANSLATE_X, 200f, 2000)
    }
}