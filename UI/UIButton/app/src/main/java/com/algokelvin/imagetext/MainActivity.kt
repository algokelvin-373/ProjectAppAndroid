package com.algokelvin.imagetext

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAction = findViewById<Button>(R.id.btn_action)
        btnAction.setOnClickListener({
            Toast.makeText(this, "你点击这个按钮", Toast.LENGTH_LONG).show()
        })
    }
}