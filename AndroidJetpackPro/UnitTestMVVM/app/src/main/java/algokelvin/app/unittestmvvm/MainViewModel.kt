package algokelvin.app.unittestmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    fun createData(dt: String): LiveData<String> {
        val data = MutableLiveData<String>()
        data.postValue(dt)
        return data
    }

}