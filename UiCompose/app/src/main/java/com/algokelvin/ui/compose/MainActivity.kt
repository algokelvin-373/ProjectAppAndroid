package com.algokelvin.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.algokelvin.ui.compose.ui.theme.UiComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiComposeLayout()
        }
    }
}

@Composable
fun TitleText(name: String) {
    Text(
        text = "Hello $name!",
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun SubTitleText(name: String) {
    Text(
        text = name,
        color = Color.Black,
        fontSize = 16.sp,
    )
}

@Composable
fun ImageIcon(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "Sample Image",
        modifier = Modifier
            .size(300.dp)
            .padding(bottom = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun UiComposeLayout() {
    UiComposeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText("Jetpack Compose")
            Box(modifier = Modifier.height(2.dp))
            SubTitleText("My Logo is Jetpack Compose")
            Box(modifier = Modifier.height(2.dp))
            ImageIcon(R.drawable.ic_compose)
        }
    }
}