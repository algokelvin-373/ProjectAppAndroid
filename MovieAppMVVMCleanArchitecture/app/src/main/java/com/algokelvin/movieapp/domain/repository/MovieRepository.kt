package com.algokelvin.movieapp.domain.repository

import com.algokelvin.movieapp.data.model.movie.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>?
    suspend fun updateMovies(): List<Movie>?
}