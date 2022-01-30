package com.algovin373.project.moviecatalog.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algovin373.project.moviecatalog.repository.TVShowRepository
import com.algovin373.project.moviecatalog.viewmodel.TVShowViewModel
import io.reactivex.disposables.CompositeDisposable

class TVShowViewModelFactory(val tvShowRepository: TVShowRepository,
                             val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            : T = TVShowViewModel(tvShowRepository, compositeDisposable) as T
}