package com.algovin373.project.moviecatalog.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.viewmodel.DetailMovieViewModel
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModelFactory (val movieRepository: MovieRepository,
                                   val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            : T = DetailMovieViewModel(movieRepository, compositeDisposable) as T
}