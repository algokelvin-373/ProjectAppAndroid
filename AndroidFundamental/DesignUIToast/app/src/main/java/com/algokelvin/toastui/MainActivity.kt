package com.algokelvin.toastui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSnackBar.setOnClickListener {
            Snackbar.make(coordinatorLayout, "Show with SnackBar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}