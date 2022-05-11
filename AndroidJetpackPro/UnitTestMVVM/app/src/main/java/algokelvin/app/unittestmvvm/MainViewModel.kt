package algokelvin.app.unittestmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val data = MutableLiveData<String>()

    fun getData(dt: String): LiveData<String> {
        data.postValue(dt)
        return data
    }

}