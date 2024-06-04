package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class MovieViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
): ViewModel() {

    fun getMovies() = liveData {
        val listMovie = getMoviesUseCase.execute()
        emit(listMovie)
    }

    fun updateMovies() = liveData {
        val updateListMovie = updateMoviesUseCase.execute()
        emit(updateListMovie)
    }

}