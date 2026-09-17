package com.algokelvin.uibutton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EdtTxtViewModel: ViewModel() {

    fun rqsStatus(pin: String): LiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        if (pin == "123456") {
            status.postValue(true)
        } else {
            status.postValue(false)
        }
        return status
    }
}