package algokelvin.app.jetpackcompose

import algokelvin.app.jetpackcompose.ui.theme.JetpackComposeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

class RowLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RowLayoutPreview()
        }
    }
}

@Composable
fun TextViewTwo(name: String) {
    Text(
        text = "$name!",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(4.dp)
            .border(2.dp, color = Color.Blue)
            .padding(4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun RowLayoutPreview() {
    JetpackComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .fillMaxSize()
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextViewTwo("Android")
                TextViewTwo("Jetpack")
                TextViewTwo("Compose")
            }
        }
    }
}