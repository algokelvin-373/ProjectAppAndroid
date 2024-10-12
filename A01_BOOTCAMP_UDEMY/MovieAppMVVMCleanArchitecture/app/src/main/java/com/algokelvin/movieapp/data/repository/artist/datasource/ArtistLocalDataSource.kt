package com.algokelvin.movieapp.data.repository.artist.datasource

import com.algokelvin.movieapp.data.model.artist.Artist

interface ArtistLocalDataSource {
    suspend fun getArtistsFromDB(): List<Artist>
    suspend fun saveArtistsToDB(artists: List<Artist>)
    suspend fun clearAll()
}