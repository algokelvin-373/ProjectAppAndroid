package com.algokelvin.app.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    protected fun toast(message: String, isLong: Boolean = false) {
        Toast.makeText(this, message,
            if (isLong)
                Toast.LENGTH_LONG
            else
                Toast.LENGTH_SHORT
        ).show()
    }
}