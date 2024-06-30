package algokelvin.app.conversion.ui.converter

import algokelvin.app.conversion.repository.ConverterDatabaseRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import javax.inject.Inject

class ConversionViewModelFactory @Inject constructor(
    private val repository: ConverterDatabaseRepository
): NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConversionViewModel(repository) as T
    }
}