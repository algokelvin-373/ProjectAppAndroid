package com.algokelvin.moviecatalog.ui.activity.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.moviecatalog.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModelFactory (
    val movieRepository: MovieRepository,
    val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailMovieViewModel(movieRepository, compositeDisposable) as T

}