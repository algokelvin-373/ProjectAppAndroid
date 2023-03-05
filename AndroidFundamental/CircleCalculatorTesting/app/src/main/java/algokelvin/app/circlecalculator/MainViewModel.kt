package algokelvin.app.circlecalculator

import algokelvin.app.circlecalculator.circle.CircleCalculator
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

class MainViewModel(val circleCalculator: CircleCalculator): ViewModel() {
    var radius = MutableLiveData<String>()
    var area = MutableLiveData<String>()
    var circumference = MutableLiveData<String>()

    val areaValue: LiveData<String>
        get() = area

    val circumferenceValue: LiveData<String>
        get() = circumference

    fun calculate() {
        try {
            val radius = radius.value?.toDouble()
            if (radius != null) {
                getArea(radius)
                getCircumference(radius)
            } else {
                area.value = null
                circumference.value = null
            }
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
            area.value = null
            circumference.value = null
        }
    }

    fun getArea(radius: Double) {
        val calculateArea = circleCalculator.calculateArea(radius)
        area.value = calculateArea.toString()
    }

    fun getCircumference(radius: Double) {
        val calculateCircumference = circleCalculator.calculateCircumference(radius)
        circumference.value = calculateCircumference.toString()
    }

}