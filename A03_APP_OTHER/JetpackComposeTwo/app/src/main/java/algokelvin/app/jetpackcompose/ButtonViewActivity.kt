package algokelvin.app.jetpackcompose

import algokelvin.app.jetpackcompose.ui.theme.JetpackComposeTheme
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ButtonViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonViewPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonViewPreview() {
    val context = LocalContext.current
    JetpackComposeTheme {
        Button(onClick = {
            Toast.makeText(context, "Go to Column Layout", Toast.LENGTH_LONG).show()
            val intentToColumnLayout = Intent(context, ColumnLayoutActivity::class.java)
            context.startActivity(intentToColumnLayout)
        }) {
            Text("Show Toast")
            PaddingValues(8.dp)
        }
    }
}