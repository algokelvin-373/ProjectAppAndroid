package algokelvin.app.movietvclient.domain.usecase.tvShow

import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import algokelvin.app.movietvclient.domain.repository.TvShowRepository

class GetTvShowsUseCase(private val tvShowRepository: TvShowRepository) {
    suspend fun execute(): List<TvShow>? = tvShowRepository.getTvShows()
}