package kelvin.algorithm.appsamplelocalization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class LocalizationAppTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var lang = intent.getStringExtra("data")
        if(lang == null) lang = LANG.EN
        Log.i("language", lang.toString())
        setDefaultLang(lang)

        setContentView(R.layout.activity_localization_other)
    }
}
