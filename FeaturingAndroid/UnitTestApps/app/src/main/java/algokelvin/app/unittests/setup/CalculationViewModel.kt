package algokelvin.app.unittests.setup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculationViewModel(
    private val calculations: Calculations
): ViewModel() {

    private var radius = MutableLiveData<String>()

    private var area = MutableLiveData<String>()
    val areaValue: LiveData<String>
        get() = area

    private var circumference = MutableLiveData<String>()
    val circumferenceValue: LiveData<String>
        get() = circumference

    fun calculateCircle(radius: Double) {
        try {
            calculateArea(radius)
            calculateCircumference(radius)
        } catch (e: Exception) {
            Log.i("Message", "Error: ${e.message.toString()}")
        }
    }

    fun calculate() {
        try {
            val radiusValue = radius.value?.toDouble()
            if (radiusValue != null) {
                calculateArea(radiusValue)
                calculateCircumference(radiusValue)
            } else {
                area.value = null
                circumference.value = null
            }
        } catch (e: Exception) {
            Log.i("Message", "Error: ${e.message.toString()}")
        }
    }

    private fun calculateArea(radius: Double) {
        val calculateArea = calculations.calculateArea(radius)
        area.value = calculateArea.toString()
    }

    private fun calculateCircumference(radius: Double) {
        val calculateCircumference = calculations.calculateCircumference(radius)
        circumference.value = calculateCircumference.toString()
    }

}