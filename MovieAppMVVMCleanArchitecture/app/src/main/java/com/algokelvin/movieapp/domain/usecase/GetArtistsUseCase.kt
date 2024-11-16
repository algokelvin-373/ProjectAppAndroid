package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.artist.Artist
import com.algokelvin.movieapp.domain.repository.ArtistRepository

class GetArtistsUseCase(private val artistRepository: ArtistRepository) {
    suspend fun execute(): List<Artist>? = artistRepository.getArtists()
}