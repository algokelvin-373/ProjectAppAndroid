package algokelvin.app.languagechangeone

import algokelvin.app.languagechangeone.databinding.ActivityMainBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonOnNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefLang = getSharedPreferences("save_language", Context.MODE_PRIVATE)
        when(prefLang.getString("lang", LANG.EN) ?: LANG.EN) {
            LANG.IN -> buttonOnNow = binding.btnIndonesian
            LANG.EN -> buttonOnNow = binding.btnEnglish
            LANG.JA -> buttonOnNow = binding.btnJapanese
        }
        buttonOnNow.setBackgroundResource(android.R.drawable.button_onoff_indicator_on);

        binding.btnIndonesian.setOnClickListener { changeLanguage(LANG.IN, getString(R.string.indonesian)) }
        binding.btnEnglish.setOnClickListener { changeLanguage(LANG.EN, getString(R.string.english)) }
        binding.btnJapanese.setOnClickListener { changeLanguage(LANG.JA, getString(R.string.japanese)) }
    }

    private fun changeLanguage(lang: String, language: String) {
        setLanguage(lang)
        finish()
        startActivity(Intent(this, this.javaClass))
        Toast.makeText(this, getString(R.string.changeLanguage, language), Toast.LENGTH_SHORT).show()
    }

}