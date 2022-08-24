package algorithm.kelvin.app.runnable_02

import android.content.Context
import android.util.Log

data class RunnableMethod(val handler: android.os.Handler? = null, val context: Context) {
    private var runnable: Runnable? = null

    fun setValueLogin(status: Boolean) {
        val preferences = context.getSharedPreferences("status_login", Context.MODE_PRIVATE)
        preferences.edit().putBoolean("status", status).apply()
    }

    fun getValueLogin(): Boolean {
        val preferences = context.getSharedPreferences("status_login", Context.MODE_PRIVATE)
        return preferences?.getBoolean("status", false) ?: false
    }

    //TODO: This is function system runnable
    private fun systemRunnable() {
        runnable = object: Runnable {
            override fun run() {
                Log.i("android-runnable", "Runnable is work")
                if (!getValueLogin()) {
                    Log.i("android-runnable", "Runnable has been stopped")
                    handler?.removeCallbacks(this)
                }
                else {
                    handler?.postDelayed(this, 1000)
                }
            }
        }
    }

    //TODO: This is function to Start Runnable
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun startRunnable() {
        systemRunnable()
        Log.i("android-runnable", "Runnable start")
        handler?.postDelayed(runnable, 5000)
        Log.i("android-runnable", "You have been called Runnable")
    }
}