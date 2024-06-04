package algokelvin.app.movietvclient.data.repository.movies.datasource

import algokelvin.app.movietvclient.data.model.movies.Movie

interface MovieCacheDataSource {
    suspend fun getMoviesFromCache(): List<Movie>
    suspend fun saveMoviesFromCache(movies: List<Movie>)
}