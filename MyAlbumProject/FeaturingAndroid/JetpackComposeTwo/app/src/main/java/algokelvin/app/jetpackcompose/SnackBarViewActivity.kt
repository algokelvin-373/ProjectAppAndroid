package algokelvin.app.jetpackcompose

import algokelvin.app.jetpackcompose.ui.theme.JetpackComposeTheme
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnackBarPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SnackBarPreview() {
    JetpackComposeTheme {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        
        Scaffold(scaffoldState = scaffoldState) {
            Button(onClick = {
                coroutineScope.launch {
                    val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = "This is a message",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Indefinite
                    )
                    when(snackBarResult) {
                        SnackbarResult.ActionPerformed -> {
                            Log.i("MY_TAG", "You click action snack bar")
                        }
                        SnackbarResult.Dismissed -> {
                            Log.i("MY_TAG", "You let snack bar is dismissed")
                        }
                    }
                }
            }) {
                Text("Display Snack Bar")
            }
        }
    }
}