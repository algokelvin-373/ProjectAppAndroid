package algokelvin.app.circlecalculator

import algokelvin.app.circlecalculator.circle.CircleCalculator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val circleCalculator: CircleCalculator
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(circleCalculator) as T
    }

}