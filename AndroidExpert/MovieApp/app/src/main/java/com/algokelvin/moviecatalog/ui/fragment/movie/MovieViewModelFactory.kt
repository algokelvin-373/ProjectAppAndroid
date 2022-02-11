package com.algokelvin.moviecatalog.ui.fragment.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.moviecatalog.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class MovieViewModelFactory(
    val movieRepository: MovieRepository,
    val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MovieViewModel(movieRepository, compositeDisposable) as T

}