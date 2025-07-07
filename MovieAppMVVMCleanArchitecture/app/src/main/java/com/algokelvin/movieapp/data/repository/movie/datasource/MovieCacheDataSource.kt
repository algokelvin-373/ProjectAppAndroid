package com.algokelvin.movieapp.data.repository.movie.datasource

import com.algokelvin.movieapp.data.model.movie.Movie

interface MovieCacheDataSource {
    suspend fun getMoviesFromCache(): List<Movie>
    suspend fun saveMoviesToCache(movies: List<Movie>)
}