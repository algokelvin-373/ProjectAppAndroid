package algokelvin.app.conversion.compose.base

import algokelvin.app.conversion.compose.history.HistoryScreen
import algokelvin.app.conversion.ui.converter.ConversionViewModel
import algokelvin.app.conversion.ui.converter.ConversionViewModelFactory
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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

    val configuration = LocalConfiguration.current
    var isLandscape by remember { mutableStateOf(false) }
    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            isLandscape = true
            Row(
                modifier = modifier
                    .padding(30.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TopScreen(
                    list,
                    conversionViewModel.selectedConversion,
                    conversionViewModel.inputText,
                    conversionViewModel.typedValue,
                    isLandscape
                ) { message1, message2 ->
                    conversionViewModel.addResult(message1, message2)
                }
                Spacer(modifier = Modifier.height(10.dp))
                HistoryScreen(historyList, { item ->
                    conversionViewModel.removeResult(item)
                }, {
                    conversionViewModel.clearAll()
                })
            }
        } else -> {
            isLandscape = false
            Column(modifier = modifier.padding(30.dp)) {
                TopScreen(
                    list,
                    conversionViewModel.selectedConversion,
                    conversionViewModel.inputText,
                    conversionViewModel.typedValue,
                    isLandscape
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
    }
}
