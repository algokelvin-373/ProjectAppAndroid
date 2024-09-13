package algokelvin.app.movietvclient.presentation.movie

import algokelvin.app.movietvclient.domain.usecase.movie.GetMoviesUseCase
import algokelvin.app.movietvclient.domain.usecase.movie.UpdateMoviesUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateMoviesUseCase: UpdateMoviesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(getMoviesUseCase, updateMoviesUseCase) as T
    }
}