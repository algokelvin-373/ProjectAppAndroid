package com.algokelvin.jetpack.componse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            mainActivity()
        }

    }
}

@Preview
@Composable
fun mainActivity() {
    Row {
        Image(
            painter = painterResource(R.drawable.ic_logo_algokelvin),
            contentDescription = "Contact profile picture"
        )
        Column {
            Text(text = "ALGOKELVIN")
            Text(text = "AlgoKelvin is an Online Class to enhance the development of \"MAKE SOFTWARE\" skills.\n" +
                    "\n" +
                    "Come on, follow Algokelvin and you will be taught various methods and techniques easy and precise in making software. Here it is also taught to learn team collaboration which is a provision to enter the world of Technology work \uD83D\uDE07\uD83D\uDE07\n" +
                    "\n" +
                    "Go International")
            Text(text = "Youtube: https://www.youtube.com/c/AlgoKelvin373")
        }
    }
}