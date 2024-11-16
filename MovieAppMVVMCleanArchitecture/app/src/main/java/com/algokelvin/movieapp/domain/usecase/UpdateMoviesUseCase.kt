package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.movie.Movie
import com.algokelvin.movieapp.domain.repository.MovieRepository

class UpdateMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(): List<Movie>? = movieRepository.updateMovies()
}