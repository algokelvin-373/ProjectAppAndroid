package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.artist.Artist

interface ArtistRepository {
    suspend fun getArtists(): List<Artist>?
    suspend fun updateArtists(): List<Artist>?
}