package com.algokelvin.app.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.app.di.DependencyFactory
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            DependencyFactory.buildMainViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel not found")
        }
    }
}