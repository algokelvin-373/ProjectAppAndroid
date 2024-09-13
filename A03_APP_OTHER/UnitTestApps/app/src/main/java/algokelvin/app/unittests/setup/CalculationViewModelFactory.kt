package algokelvin.app.unittests.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CalculationViewModelFactory(
    private val calculations: Calculations
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalculationViewModel(calculations) as T
    }

}