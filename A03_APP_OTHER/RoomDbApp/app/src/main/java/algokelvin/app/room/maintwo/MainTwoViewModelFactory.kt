package algokelvin.app.room.maintwo

import algokelvin.app.room.db.SubscriberRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainTwoViewModelFactory(
    private val repository: SubscriberRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainTwoViewModel::class.java)) {
            return MainTwoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown")
    }

}