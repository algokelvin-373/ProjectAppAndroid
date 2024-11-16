package com.algokelvin.movieapp.data.repository.artist.datasourceImpl

import com.algokelvin.movieapp.data.api.MovieApiService
import com.algokelvin.movieapp.data.model.artist.ArtistList
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import retrofit2.Response

class ArtistRemoteDataSourceImpl(
    private val service: MovieApiService,
    private val apiKey: String,
): ArtistRemoteDataSource {
    override suspend fun getArtists(): Response<ArtistList> = service.getArtistListPopular(apiKey)
}