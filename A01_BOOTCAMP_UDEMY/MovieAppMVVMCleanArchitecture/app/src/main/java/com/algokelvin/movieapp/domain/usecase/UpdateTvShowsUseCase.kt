package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.tv.TvShow
import com.algokelvin.movieapp.domain.repository.TvShowRepository

class UpdateTvShowsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend fun execute(): List<TvShow>? = tvShowRepository.updateTvShows()
}