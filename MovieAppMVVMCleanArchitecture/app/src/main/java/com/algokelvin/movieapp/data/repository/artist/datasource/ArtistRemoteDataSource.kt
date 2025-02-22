package com.algokelvin.movieapp.data.repository.artist.datasource

import com.algokelvin.movieapp.data.model.artist.ArtistList
import retrofit2.Response

interface ArtistRemoteDataSource {
    suspend fun getArtists(): Response<ArtistList>
}