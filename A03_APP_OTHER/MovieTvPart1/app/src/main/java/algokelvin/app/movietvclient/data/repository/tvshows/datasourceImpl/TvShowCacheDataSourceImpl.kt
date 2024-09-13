package algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl

import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowCacheDataSource

class TvShowCacheDataSourceImpl: TvShowCacheDataSource {
    private var tvShowsList = ArrayList<TvShow>()

    override suspend fun getTvShowsFromCache(): List<TvShow> {
        return tvShowsList
    }

    override suspend fun saveTvShowsFromCache(tvShows: List<TvShow>) {
        tvShowsList.clear()
        tvShowsList = ArrayList(tvShows)
    }
}