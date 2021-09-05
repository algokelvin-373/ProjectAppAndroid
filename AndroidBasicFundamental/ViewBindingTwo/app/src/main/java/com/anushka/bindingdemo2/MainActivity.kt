package com.anushka.bindingdemo2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.control_button)
        button.setOnClickListener {
            startOrStopProgressBar()
        }
    }

    private fun startOrStopProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        if (progressBar.visibility == View.GONE) {
            progressBar.visibility = View.VISIBLE
            button.text = "Stop"
        } else {
            progressBar.visibility = View.GONE
            button.text = "Start"
        }
    }
}

