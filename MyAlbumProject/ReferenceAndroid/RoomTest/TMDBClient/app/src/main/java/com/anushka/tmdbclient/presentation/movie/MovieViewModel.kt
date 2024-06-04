package com.anushka.tmdbclient.presentation.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anushka.tmdbclient.domain.usecase.GetMoviesUseCase
import com.anushka.tmdbclient.domain.usecase.UpdateMoviesUsecase

class MovieViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMoviesUsecase: UpdateMoviesUsecase
): ViewModel() {

    init {
        Log.i("SCOTEST"," new movie view model instance created ")
    }

    fun getMovies() = liveData {
        val movieList = getMoviesUseCase.execute()
        emit(movieList)
    }

    fun updateMovies() = liveData {
        val movieList = updateMoviesUsecase.execute()
        emit(movieList)
    }

    }





