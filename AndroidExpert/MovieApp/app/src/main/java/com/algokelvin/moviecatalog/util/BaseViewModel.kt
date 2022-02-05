package com.algokelvin.moviecatalog.util

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(private val compositeDisposable: CompositeDisposable): ViewModel() {
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}