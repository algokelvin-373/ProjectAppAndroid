package com.algokelvin.movieapp.presentation.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.domain.usecase.GetTvShowsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateTvShowsUseCase

class TvShowViewModel(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase,
): ViewModel() {
    fun getTvShows() = liveData {
        val tvShowsList = getTvShowsUseCase.execute()
        emit(tvShowsList)
    }

    fun updateTvShows() = liveData {
        val updateTvShowsList = updateTvShowsUseCase.execute()
        emit(updateTvShowsList)
    }
}