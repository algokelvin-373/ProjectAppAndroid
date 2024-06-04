package algokelvin.app.jetpackcompose

import algokelvin.app.jetpackcompose.ui.theme.JetpackComposeTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class RecyclerViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerViewPreview()
        }
    }
}

@Composable
fun LayoutText(txt: String, selectedItem: (String) -> (Unit)) {
    Text(
        text = txt,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(10.dp)
            .clickable {
                selectedItem("$txt is Selected")
            }
    )
}

@Composable
fun RecyclerViewPreview() {
    JetpackComposeTheme {
        val context = LocalContext.current

        // Using 'LazyColumn' to play RecyclerView
        LazyColumn {
            items(50) {
                LayoutText("User Name $it") { msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
                Divider(color = Color.Black, thickness = 5.dp)
            }
        }
    }
}