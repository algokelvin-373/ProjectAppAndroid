package com.algokelvin.movieapp.data.repository.movie.datasource

import com.algokelvin.movieapp.data.model.movie.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
}