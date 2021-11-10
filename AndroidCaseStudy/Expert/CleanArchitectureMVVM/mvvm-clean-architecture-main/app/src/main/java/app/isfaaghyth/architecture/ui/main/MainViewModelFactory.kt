package app.isfaaghyth.architecture.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.isfaaghyth.architecture.di.DepsFactory
import java.lang.IllegalArgumentException

class MainViewModelFactory constructor(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            DepsFactory.buildMainViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }

}