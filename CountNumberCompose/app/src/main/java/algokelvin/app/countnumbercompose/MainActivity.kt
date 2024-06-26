package algokelvin.app.countnumbercompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import algokelvin.app.countnumbercompose.ui.theme.EffectsDemo1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EffectsDemo1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    var round by remember { mutableStateOf(1) }
    var total by remember { mutableStateOf(0.0) }
    var input by remember { mutableStateOf("") }

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    
    Scaffold(scaffoldState = scaffoldState) {
        // LaunchedEffect
        LaunchedEffect(key1 = round) {
            Toast.makeText(context, context.getString(R.string.start_your_app), Toast.LENGTH_LONG).show() // Using Toast
            scaffoldState.snackbarHostState.showSnackbar( // Using scaffoldState
                message = context.getString(R.string.enjoy_this),
                duration = SnackbarDuration.Short
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = context.getString(R.string.total_count, total.toString()),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.DarkGray
            )
            OutlinedTextField(
                placeholder = { Text(context.getString(R.string.enter_value)) },
                value = input,
                onValueChange = {
                    input = it
                },
                textStyle = TextStyle(
                    color = Color.LightGray,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                label = {
                    Text(
                        text = context.getString(R.string.new_count),
                        fontSize = 30.sp
                    )},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    total += input.toDouble()
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = context.getString(R.string.count_updated),
                            duration = SnackbarDuration.Short
                        )
                    }
                    if (total > 300) {
                        total = 0.0
                        input = ""
                        round++
                    }
                }
            ) {
                Text(
                    text = context.getString(R.string.count),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

