package com.algokelvin.movieapp.data.repository.movie.datasourceImpl

import com.algokelvin.movieapp.data.db.MovieDao
import com.algokelvin.movieapp.data.model.movie.Movie
import com.algokelvin.movieapp.data.repository.artist.datasource.ArtistLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(private val movieDao: MovieDao): ArtistLocalDataSource {
    override suspend fun getMoviesFromDB(): List<Movie> = movieDao.getAllMovies()

    override suspend fun saveMoviesToDB(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.saveMovies(movies)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.deleteAllMovies()
        }
    }
}