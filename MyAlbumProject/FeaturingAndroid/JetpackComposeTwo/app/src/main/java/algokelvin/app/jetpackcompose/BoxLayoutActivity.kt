package algokelvin.app.jetpackcompose

import algokelvin.app.jetpackcompose.ui.theme.JetpackComposeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class BoxLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxLayoutPreview()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BoxLayoutPreview() {
    JetpackComposeTheme {
        Box(
            modifier = Modifier
                .background(color = Color.Blue)
                .size(150.dp, 300.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Yellow)
                    .align(Alignment.TopEnd)
                    .size(75.dp, 150.dp)
            ) {

            }
            Text(
                text = "Hello",
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(color = Color.Black)
                    .border(4.dp, color = Color.Red)
                    .padding(8.dp)
            )
        }
    }
}