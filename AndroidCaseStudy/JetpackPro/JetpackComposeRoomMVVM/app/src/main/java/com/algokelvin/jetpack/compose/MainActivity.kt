package com.algokelvin.jetpack.compose

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    pageActivity()
                }
            }
        }
    }
}

@Preview
@Composable
fun pageActivity() {
    Text(text = "Hello Algokelvin. I'm play Jetpack Compose")
}