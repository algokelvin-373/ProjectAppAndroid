package algokelvin.app.room.maintwo

import algokelvin.app.room.db.Subscriber
import algokelvin.app.room.db.SubscriberRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainTwoViewModel(private val subscriberRepository: SubscriberRepository): ViewModel() {

    val subscribers = subscriberRepository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val btnSaveOrUpdate = MutableLiveData<String>()
    val btnDeleteOrClearAll = MutableLiveData<String>()

    init {
        btnSaveOrUpdate.value = "Save"
        btnDeleteOrClearAll.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    fun deleteOrClearAll() {

    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.insert(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.update(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.delete(subscriber)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.deleteAll()
    }

}