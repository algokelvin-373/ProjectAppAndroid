package algokelvin.app.countplusminlivedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainController(random: Int) {
    private var result = MutableLiveData<Int>()
    private var count = 0

    init {
        count = random
    }

    fun setResult(): LiveData<Int> {
        result.postValue(count)
        return result
    }

    fun setPlus(data: Int): LiveData<Int> {
        count += data
        result.postValue(count)
        return result
    }

    fun setMin(data: Int): LiveData<Int> {
        count -= data
        result.postValue(count)
        return result
    }

}