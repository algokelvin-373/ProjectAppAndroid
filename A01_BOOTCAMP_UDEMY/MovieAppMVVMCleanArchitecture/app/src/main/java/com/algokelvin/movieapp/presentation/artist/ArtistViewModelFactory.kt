package com.algokelvin.movieapp.presentation.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.domain.usecase.GetArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.GetMoviesUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateArtistsUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateMoviesUseCase

class ArtistViewModelFactory(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val updateArtistsUseCase: UpdateArtistsUseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistViewModel(getArtistsUseCase, updateArtistsUseCase) as T
    }
}