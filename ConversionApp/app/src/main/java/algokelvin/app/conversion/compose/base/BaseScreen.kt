package algokelvin.app.conversion.compose.base

import algokelvin.app.conversion.ui.converter.ConversionViewModel
import algokelvin.app.conversion.ui.converter.ConversionViewModelFactory
import algokelvin.app.conversion.compose.history.HistoryScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BaseScreen(
    factory: ConversionViewModelFactory,
    modifier: Modifier = Modifier,
    conversionViewModel: ConversionViewModel = viewModel(factory = factory)
) {
    val list = conversionViewModel.getConversions()
    val historyList = conversionViewModel.resultList.collectAsState(initial = emptyList())

    Column(modifier = modifier.padding(30.dp)) {
        TopScreen(
            list,
            conversionViewModel.selectedConversion,
            conversionViewModel.inputText,
            conversionViewModel.typedValue
        ) { message1, message2 ->
            conversionViewModel.addResult(message1, message2)
        }
        Spacer(modifier = Modifier.height(20.dp))
        HistoryScreen(historyList, { item ->
            conversionViewModel.removeResult(item)
        }, {
            conversionViewModel.clearAll()
        })
    }
}
