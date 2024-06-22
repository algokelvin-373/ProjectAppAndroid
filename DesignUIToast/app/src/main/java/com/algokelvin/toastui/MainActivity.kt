package com.algokelvin.toastui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToast.setOnClickListener { showWithToast() }
        btnSnackBar.setOnClickListener { showWithSnackBar() }
    }

    private fun showWithToast() {
        Toast.makeText(this, "Show with Toast", Toast.LENGTH_LONG).show()
    }

    private fun showWithSnackBar() {
        Snackbar.make(coordinatorLayout, "Show with SnackBar", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

}