package algokelvin.app.movietvclient.presentation.di.tvShow

import algokelvin.app.movietvclient.domain.usecase.tvShow.GetTvShowsUseCase
import algokelvin.app.movietvclient.domain.usecase.tvShow.UpdateTvShowsUseCase
import algokelvin.app.movietvclient.presentation.tvShow.TvShowViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TvShowModule {

    @TvShowScope
    @Provides
    fun provideTvShowViewModelFactory(
        getTvShowsUseCase: GetTvShowsUseCase,
        updateTvShowsUseCase: UpdateTvShowsUseCase
    ): TvShowViewModelFactory {
        return TvShowViewModelFactory(getTvShowsUseCase, updateTvShowsUseCase)
    }

}