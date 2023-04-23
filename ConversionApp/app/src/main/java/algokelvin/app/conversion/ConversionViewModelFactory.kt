package algokelvin.app.conversion

import algokelvin.app.conversion.repository.ConverterDatabaseRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

class ConversionViewModelFactory(
    private val repository: ConverterDatabaseRepository
): NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConversionViewModel(repository) as T
    }
}