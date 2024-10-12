package algokelvin.app.countapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var count = MutableLiveData<Int>()
    val countData: LiveData<Int>
    get() = count

    init {
        count.value = 0
    }

    fun add() {
        count.value = getCount()?.plus(1)
    }

    fun min() {
        count.value = getCount()?.minus(1)
    }

    private fun getCount(): Int? = count.value

}