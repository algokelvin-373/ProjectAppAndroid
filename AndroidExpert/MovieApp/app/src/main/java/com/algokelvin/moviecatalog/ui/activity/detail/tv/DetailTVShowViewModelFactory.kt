package com.algokelvin.moviecatalog.ui.activity.detail.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.moviecatalog.repository.TVShowRepository
import io.reactivex.disposables.CompositeDisposable

class DetailTVShowViewModelFactory (
    val tvShowRepository: TVShowRepository,
    val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailTVShowViewModel(tvShowRepository, compositeDisposable) as T

}