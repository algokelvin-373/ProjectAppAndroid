package algokelvin.app.conversion.ui.converter

import algokelvin.app.conversion.model.Conversion
import algokelvin.app.conversion.model.ConversionResult
import algokelvin.app.conversion.repository.ConverterDatabaseRepository
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConversionViewModel(private val repository: ConverterDatabaseRepository): ViewModel() {

    val selectedConversion: MutableState<Conversion?> = mutableStateOf(null)
    val inputText: MutableState<String> = mutableStateOf("")
    val typedValue = mutableStateOf("0.0")

    fun getConversions() = listOf(
        Conversion(1, "Pounds to Kilograms", "lbs", "kg", 0.453592),
        Conversion(2, "Kilograms to Pounds", "kg", "lbs", 2.20462),
        Conversion(3, "Yards to Meters", "yd", "m", 0.9144),
        Conversion(4, "Meters to Yards", "m", "yd", 1.09361),
        Conversion(5, "Miles to Kilometers", "mil", "km", 1.60934),
        Conversion(6, "Kilometers to Miles", "km", "mil", 0.621371),
    )

    val resultList = repository.getAllResult()

    fun addResult(msg1: String, msg2: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertResult(ConversionResult(msg1 = msg1, msg2 = msg2))
        }
    }

    fun removeResult(item: ConversionResult) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteResult(item)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllResult()
        }
    }

}