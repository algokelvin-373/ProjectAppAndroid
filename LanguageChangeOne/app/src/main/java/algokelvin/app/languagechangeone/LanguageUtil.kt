package algokelvin.app.languagechangeone

import android.content.Context
import android.os.Build
import java.util.*

object LanguageUtil {
    fun savePref(context: Context, lang: String) {
        val pref = context.getSharedPreferences("save_language", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("lang", lang)
        editor.apply()
    }
}

object LANG {
    const val IN = "in"
    const val EN = "en"
    const val JA = "ja"
}

fun Context.setLanguage(lang: String) {
    val dm = resources.displayMetrics
    var conf = resources.configuration
    conf.setLocale(Locale(lang))
    LanguageUtil.savePref(this, lang)
    resources.updateConfiguration(conf, dm)
}