package com.algokelvin.movieapp.data.repository.movie.datasourceImpl

import com.algokelvin.movieapp.data.api.MovieApiService
import com.algokelvin.movieapp.data.model.movie.MovieList
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistRemoteDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val service: MovieApiService,
    private val apiKey: String,
): MovieRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> = service.getMovieListPopular(apiKey)
}