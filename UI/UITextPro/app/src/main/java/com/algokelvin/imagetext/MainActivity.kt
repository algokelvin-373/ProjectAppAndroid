package com.algokelvin.imagetext

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text1 = findViewById<TextView>(R.id.text1)
        val text2 = findViewById<TextView>(R.id.text2)
        val text3 = findViewById<TextView>(R.id.text3)
        val text4 = findViewById<TextView>(R.id.text4)
        val text5 = findViewById<TextView>(R.id.text5)
        val text6 = findViewById<TextView>(R.id.text6)
        val text7 = findViewById<TextView>(R.id.text7)

        // Text 1
        text1.text = "Text1 12sp black"
        text1.setTextColor(Color.BLACK)
        text1.textSize = 12f
        text1.gravity = Gravity.CENTER

        // Text 2
        text2.text = "Text2 14sp red"
        text2.setTextColor(Color.RED)
        text2.textSize = 14f
        text2.gravity = Gravity.CENTER

        // Text 3
        text3.text = "Text3 20sp blue bold"
        text3.setTextColor(Color.BLUE)
        text3.textSize = 20f
        text3.setTypeface(null, Typeface.BOLD)
        text3.gravity = Gravity.CENTER

        // Text 4
        text4.text = "Text4 22sp green italic"
        text4.setTextColor(Color.GREEN)
        text4.textSize = 22f
        text4.setTypeface(null, Typeface.ITALIC)
        text4.gravity = Gravity.CENTER
    }
}