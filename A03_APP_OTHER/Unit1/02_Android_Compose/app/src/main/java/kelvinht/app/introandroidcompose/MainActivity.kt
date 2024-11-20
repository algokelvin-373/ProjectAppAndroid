package kelvinht.app.introandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kelvinht.app.introandroidcompose.ui.theme.IntroAndroidComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroAndroidComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DefaultPreview()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Red) {
        Text(
            text = "Hello, $name!. I'm learn Android Jetpack Compose", // Set Text
            color = Color.White, // Set Color Text = White
            modifier = Modifier.padding(16.dp) // Set padding = 16dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntroAndroidComposeTheme {
        Greeting("AlgoKelvin")
    }
}