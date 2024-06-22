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

        /*
        Make TextView translate X to coordinate 200 with duration 2 seconds.
        This method use the module 'animator-featuring'
         */
        animatorController.animatorTextView(txt_hello, TRANSLATE_X, 200f, 2000)
    }
}