package kelvinht.app.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kelvinht.app.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }
}

var currentStep = 1
const val STEP_1 = 1
const val STEP_2 = 2
const val STEP_3 = 3
const val STEP_4 = 4

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {

    LemonadeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Log.i("cek-log->", currentStep.toString())
            when(currentStep) {
                STEP_1 -> BehaviourApp(
                    STEP_2,
                    R.string.lemon_tree,
                    R.drawable.lemon_tree,
                    R.string.lemon_tree_description
                )
                STEP_2 -> BehaviourApp(
                    STEP_3,
                    R.string.lemon,
                    R.drawable.lemon_squeeze,
                    R.string.lemon_description
                )
                STEP_3 -> BehaviourApp(
                    STEP_4,
                    R.string.glass_of_lemonade,
                    R.drawable.lemon_drink,
                    R.string.glass_of_lemonade_description
                )
                STEP_4 -> BehaviourApp(
                    STEP_1,
                    R.string.empty_glass,
                    R.drawable.lemon_restart,
                    R.string.empty_glass_description
                )
            }
        }
    }
}

@Composable
fun BehaviourApp(step: Int, text: Int, img: Int, description: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(text))
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(img),
            contentDescription = stringResource(description),
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    currentStep++
                    Log.i("cek-log", currentStep.toString())
                }
        )
    }
}
