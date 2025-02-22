package com.algokelvin.movieapp.data.repository.movie

import android.util.Log
import com.algokelvin.movieapp.data.model.movie.Movie
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieCacheDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieLocalDataSource
import com.algokelvin.movieapp.data.repository.movie.datasource.MovieRemoteDataSource
import com.algokelvin.movieapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remote: MovieRemoteDataSource,
    private val local: MovieLocalDataSource,
    private val cache: MovieCacheDataSource,
): MovieRepository {
    override suspend fun getMovies(): List<Movie> = getMoviesFromCache()

    override suspend fun updateMovies(): List<Movie> {
        val newListOfMovies = getMoviesFromAPI()
        local.clearAll()
        local.saveMoviesToDB(newListOfMovies)
        cache.saveMoviesToCache(newListOfMovies)
        return newListOfMovies
    }

    suspend fun getMoviesFromAPI(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            val response = remote.getMovies()
            val body = response.body()
            if (body != null) {
                movieList = body.results
            }
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        return movieList
    }

    suspend fun getMoviesFromDB(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = local.getMoviesFromDB()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (movieList.isNotEmpty()) {
            return movieList
        } else {
            movieList = getMoviesFromAPI()
            local.saveMoviesToDB(movieList)
        }

        return movieList
    }

    suspend fun getMoviesFromCache(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = cache.getMoviesFromCache()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (movieList.isNotEmpty()) {
            return movieList
        } else {
            movieList = getMoviesFromDB()
            cache.saveMoviesToCache(movieList)
        }

        return movieList
    }
}