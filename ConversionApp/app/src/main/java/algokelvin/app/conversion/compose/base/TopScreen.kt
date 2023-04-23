package algokelvin.app.conversion.compose.base

import algokelvin.app.conversion.compose.base.top.ConversionMenu
import algokelvin.app.conversion.compose.base.top.InputBlock
import algokelvin.app.conversion.model.Conversion
import algokelvin.app.conversion.ui.base_screen.top_screen.ResultBlock
import android.util.Log
import androidx.compose.runtime.*
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun TopScreen(
    list: List<Conversion>,
    selectedConversion: MutableState<Conversion?>,
    inputText: MutableState<String>,
    typedValue: MutableState<String>,
    save: (String, String) -> Unit
) {

    var toSave by remember {
        mutableStateOf(false)
    }

    ConversionMenu(list = list) {
        selectedConversion.value = it
        typedValue.value = "0.0"
    }

    selectedConversion.value?.let {
        InputBlock(conversion = it, inputText = inputText) { input ->
            Log.i("ALGOKELVIN", "User type $input")
            typedValue.value = input
            toSave = true
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
        if (toSave) {
            save(msg1, msg2)
            toSave = false
        }
        ResultBlock(msg1 = msg1, msg2 = msg2)
    }

}