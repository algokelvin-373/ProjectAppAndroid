package algokelvin.app.room.maintwo

import algokelvin.app.room.db.Subscriber
import algokelvin.app.room.db.SubscriberRepository
import algokelvin.app.room.utils.Event
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainTwoViewModel(private val subscriberRepository: SubscriberRepository): ViewModel() {

    val subscribers = subscriberRepository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val btnSaveOrUpdate = MutableLiveData<String>()
    val btnDeleteOrClearAll = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
    get() = statusMessage

    init {
        btnSaveOrUpdate.value = "Save"
        btnDeleteOrClearAll.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (inputName.value == null || inputName.value == "") {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(name = name, email = email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }

    }

    fun deleteOrClearAll() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            deleteAll()
        }
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        val newRowId = subscriberRepository.insert(subscriber)
        withContext(Dispatchers.Main) {
            if (newRowId > -1) {
                statusMessage.value = Event("$newRowId Subscriber Inserted Successfully")
            } else {
                statusMessage.value = Event("Error Occurred!!")
            }
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.update(subscriber)
        withContext(Dispatchers.Main) {
            viewForUpdateOrDelete()
            statusMessage.value = Event("Subscriber Updated Successfully")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.delete(subscriber)
        withContext(Dispatchers.Main) {
            viewForUpdateOrDelete()
            statusMessage.value = Event("Subscriber Deleted Successfully")
        }
    }

    private fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        subscriberRepository.deleteAll()
        withContext(Dispatchers.Main) {
            statusMessage.value = Event("All Subscriber Deleted Successfully")
        }
    }

    private fun viewForUpdateOrDelete() {
        inputName.value = ""
        inputEmail.value = ""
        isUpdateOrDelete = false
        btnSaveOrUpdate.value = "Save"
        btnDeleteOrClearAll.value = "Clear All"
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        btnSaveOrUpdate.value = "Update"
        btnDeleteOrClearAll.value = "Delete"
    }

}