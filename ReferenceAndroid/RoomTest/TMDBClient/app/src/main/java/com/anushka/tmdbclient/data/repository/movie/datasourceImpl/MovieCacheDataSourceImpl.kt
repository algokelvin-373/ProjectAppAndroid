package com.anushka.tmdbclient.data.repository.movie.datasourceImpl

import com.anushka.tmdbclient.data.model.movie.Movie
import com.anushka.tmdbclient.data.repository.movie.datasource.MovieCacheDataSource

class MovieCacheDataSourceImpl :
    MovieCacheDataSource {
    private var movieList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
       movieList.clear()
       movieList = ArrayList(movies)
    }
}