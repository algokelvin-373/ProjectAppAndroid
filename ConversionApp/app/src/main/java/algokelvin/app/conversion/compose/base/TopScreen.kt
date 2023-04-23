package algokelvin.app.conversion.compose.base

import algokelvin.app.conversion.model.Conversion
import algokelvin.app.conversion.compose.base.top.ConversionMenu
import algokelvin.app.conversion.compose.base.top.InputBlock
import algokelvin.app.conversion.ui.base_screen.top_screen.ResultBlock
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun TopScreen(list: List<Conversion>, save: (String, String) -> Unit) {
    val selectedConversion: MutableState<Conversion?> = remember { mutableStateOf(null) }
    val inputText: MutableState<String> = remember { mutableStateOf("") }
    val typedValue = remember { mutableStateOf("0.0") }

    ConversionMenu(list = list) {
        selectedConversion.value = it
        typedValue.value = "0.0"
    }

    selectedConversion.value?.let {
        InputBlock(conversion = it, inputText = inputText) { input ->
            Log.i("ALGOKELVIN", "User type $input")
            typedValue.value = input
        }
    }

    if (typedValue.value != "0.0") {
        val input = typedValue.value.toDouble()
        val multiply = selectedConversion.value?.multiplyBy
        val result = input * multiply!!

        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.DOWN
        val roundedResult = df.format(result)

        val msg1 = "${typedValue.value} ${selectedConversion.value?.convertFrom} is equal to"
        val msg2 = "$roundedResult ${selectedConversion.value?.convertTo}"
        save(msg1, msg2)
        ResultBlock(msg1 = msg1, msg2 = msg2)
    }

}