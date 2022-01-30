package com.algovin373.project.moviecatalog.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable

class MovieViewModelFactory(val movieRepository: MovieRepository,
                            val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>)
            : T = MovieViewModel(movieRepository, compositeDisposable) as T
}