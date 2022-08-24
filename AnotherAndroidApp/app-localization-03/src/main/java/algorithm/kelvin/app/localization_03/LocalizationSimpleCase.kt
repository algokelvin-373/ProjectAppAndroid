package algorithm.kelvin.app.localization_03

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import kelvin.algorithm.training_android_kotlin_develop.localization.simple_case.LANG
import kelvin.algorithm.training_android_kotlin_develop.localization.simple_case.LanguageUtil
import kelvin.algorithm.training_android_kotlin_develop.localization.simple_case.setDefaultLang
import kelvin.algorithm.training_android_kotlin_develop.localization.simple_case.setLanguage
import kotlinx.android.synthetic.main.activity_localization_training.*

@Suppress("DEPRECATION")
class LocalizationSimpleCase : AppCompatActivity() {
    private lateinit var buttonOnNow: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localization_training)
        setDefaultLang()

        when(LanguageUtil.getLang(this)) {
            LANG.IN -> buttonOnNow = btn_indonesian
            LANG.EN -> buttonOnNow = btn_english
            LANG.JA -> buttonOnNow = btn_japanese
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            buttonOnNow.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on))
        }
        else {
            buttonOnNow.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on))
        }

        btn_indonesian.setOnClickListener { changeLanguage(LANG.IN, getString(R.string.indonesian)) }
        btn_english.setOnClickListener { changeLanguage(LANG.EN, getString(R.string.english)) }
        btn_japanese.setOnClickListener { changeLanguage(LANG.JA, getString(R.string.japanese)) }

        btn_page_animal.setOnClickListener {
            startActivity(Intent(this, LocalizationOtherActivity::class.java))
            finish()
        }
    }
    private fun changeLanguage(lang: String, language: String) {
        setLanguage(lang)
        finish()
        startActivity(Intent(this, this.javaClass))
        Toast.makeText(this, getString(R.string.changeLanguage, language), Toast.LENGTH_SHORT).show()
    }
}
