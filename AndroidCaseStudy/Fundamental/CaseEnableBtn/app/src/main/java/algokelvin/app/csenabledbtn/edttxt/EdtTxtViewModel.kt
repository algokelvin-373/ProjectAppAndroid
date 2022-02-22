package algokelvin.app.csenabledbtn.edttxt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EdtTxtViewModel: ViewModel() {
    private val status = MutableLiveData<Boolean>()

    fun getStatus() = status

    fun rqsStatus(pin: String) {
        if (pin == "123456") {
            status.postValue(true)
        } else {
            status.postValue(false)
        }
    }
}