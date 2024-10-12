package algokelvin.app.conversion.compose.history

import algokelvin.app.conversion.model.ConversionResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HistoryScreen(
    list: State<List<ConversionResult>>,
    onCloseResult: (ConversionResult) -> Unit,
    onClearAllResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        if ((list.value).isNotEmpty()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "History", color = Color.Gray)
                OutlinedButton(onClick = {
                    onClearAllResult
                }) {
                    Text(text = "Clear All", color = Color.Gray)
                }
            }
        }
        HistoryList(
            list = list,
            onCloseResult = { item ->
                onCloseResult(item)
            }
        )
    }
}