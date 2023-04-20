package algokelvin.app.conversion

import algokelvin.app.conversion.ui.base_screen.BaseScreen
import algokelvin.app.conversion.ui.theme.ConvertionTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConversionActivityPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConversionActivityPreview() {
    BaseScreen()
}