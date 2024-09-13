package algokelvin.app.movietvclient.data.repository.movies.datasource

import algokelvin.app.movietvclient.data.model.movies.Movie

interface MovieLocalDataSource {
    suspend fun getMoviesFromDB(): List<Movie>
    suspend fun saveMoviesFromDB(movies: List<Movie>)
    suspend fun clearAll()
}