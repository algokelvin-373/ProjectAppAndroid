package com.algokelvin.imagetext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.algokelvin.imagetext.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding
            .inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.setContent {
            Text(
                text = "Hello for Jetpack Compose",
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}
