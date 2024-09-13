package algokelvin.app.movietvclient.domain.usecase.movie

import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.domain.repository.MovieRepository

class UpdateMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun execute(): List<Movie>? = movieRepository.updateMovies()
}