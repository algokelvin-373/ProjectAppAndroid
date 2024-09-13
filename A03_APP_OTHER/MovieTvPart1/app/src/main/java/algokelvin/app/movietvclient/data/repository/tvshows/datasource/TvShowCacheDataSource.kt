package algokelvin.app.movietvclient.data.repository.tvshows.datasource

import algokelvin.app.movietvclient.data.model.tvshows.TvShow

interface TvShowCacheDataSource {
    suspend fun getTvShowsFromCache(): List<TvShow>
    suspend fun saveTvShowsFromCache(tvShows: List<TvShow>)
}