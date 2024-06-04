package algokelvin.app.movietvclient.presentation.tvShow

import algokelvin.app.movietvclient.domain.usecase.tvShow.GetTvShowsUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.UpdateTvShowsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TvShowViewModelFactory(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TvShowViewModel(getTvShowsUseCase, updateTvShowsUseCase) as T
    }
}