package com.algokelvin.movieapp.data.repository.movie.datasource

import com.algokelvin.movieapp.data.model.movie.Movie

interface MovieLocalDataSource {
    suspend fun getMoviesFromDB(): List<Movie>
    suspend fun saveMoviesToDB(movies: List<Movie>)
    suspend fun clearAll()
}