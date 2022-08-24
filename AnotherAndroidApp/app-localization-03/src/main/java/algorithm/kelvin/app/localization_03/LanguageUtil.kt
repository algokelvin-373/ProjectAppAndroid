package kelvin.algorithm.training_android_kotlin_develop.localization.simple_case

import android.content.Context
import android.os.Build
import androidx.fragment.app.FragmentActivity
import java.util.*

object LanguageUtil {
    fun savePref(context: Context, lang: String) {
        val pref = context.getSharedPreferences("save_language", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("lang", lang)
        editor.apply()
    }

    fun getLang(context: Context): String {
        val pref = context.getSharedPreferences("save_language", Context.MODE_PRIVATE)
        val lang = pref.getString("lang", LANG.EN) ?: LANG.EN
        return lang
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
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        conf.setLocale(Locale(lang))
    }
    LanguageUtil.savePref(this, lang)
    resources.updateConfiguration(conf, dm)
}

fun FragmentActivity.setDefaultLang() {
    val lang = LanguageUtil.getLang(this)
    setLanguage(lang)
}