package com.algokelvin.movieapp.data.repository.movie.datasourceImpl

import com.algokelvin.movieapp.data.model.movie.Movie
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistCacheDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieCacheDataSource

class MovieCacheDataSourceImpl: MovieCacheDataSource {
    private var movieList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
        movieList.clear()
        movieList = ArrayList(movies)
    }
}