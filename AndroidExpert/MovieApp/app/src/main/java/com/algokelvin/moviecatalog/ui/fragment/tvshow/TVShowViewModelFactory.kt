package com.algokelvin.moviecatalog.ui.fragment.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.moviecatalog.repository.TVShowRepository
import io.reactivex.disposables.CompositeDisposable

class TVShowViewModelFactory(
    val tvShowRepository: TVShowRepository,
    val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TVShowViewModel(tvShowRepository, compositeDisposable) as T

}