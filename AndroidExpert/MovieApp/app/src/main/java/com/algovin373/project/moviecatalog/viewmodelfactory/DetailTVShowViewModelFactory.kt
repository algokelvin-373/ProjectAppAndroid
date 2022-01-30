package com.algovin373.project.moviecatalog.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algovin373.project.moviecatalog.repository.TVShowRepository
import com.algovin373.project.moviecatalog.viewmodel.DetailTVShowViewModel
import io.reactivex.disposables.CompositeDisposable

class DetailTVShowViewModelFactory (val tvShowRepository: TVShowRepository,
                                    val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            : T = DetailTVShowViewModel(tvShowRepository, compositeDisposable) as T
}