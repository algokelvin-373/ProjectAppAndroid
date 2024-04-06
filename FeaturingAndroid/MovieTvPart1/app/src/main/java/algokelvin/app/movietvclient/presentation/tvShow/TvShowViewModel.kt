package algokelvin.app.movietvclient.presentation.tvShow

import algokelvin.app.movietvclient.domain.usecase.tvShow.GetTvShowsUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.UpdateTvShowsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class TvShowViewModel(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
): ViewModel() {

    fun getTvShow() = liveData {
        val listTvShow = getTvShowsUseCase.execute()
        emit(listTvShow)
    }

    fun updateTvShow() = liveData {
        val updateListTvShow = updateTvShowsUseCase.execute()
        emit(updateListTvShow)
    }

}