package algokelvin.app.conversion.compose.history

import algokelvin.app.conversion.model.ConversionResult
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun HistoryList(
    list: State<List<ConversionResult>>,
    onCloseResult: (ConversionResult) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(
            items = list.value,
            key = { item -> item.id!! }
        ) { item ->
            HistoryItem(
                message1 = item.msg1,
                message2 = item.msg2,
                onClose = {
                    onCloseResult(item)
                }
            )
        }
    }
}