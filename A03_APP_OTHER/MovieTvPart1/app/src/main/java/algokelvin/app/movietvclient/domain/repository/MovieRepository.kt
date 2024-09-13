package algokelvin.app.movietvclient.domain.repository

import algokelvin.app.movietvclient.data.model.movies.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>?
    suspend fun updateMovies(): List<Movie>?
}