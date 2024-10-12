package algokelvin.app.movietvclient.data.repository.tvshows.datasource

import algokelvin.app.movietvclient.data.model.tvshows.TvShow

interface TvShowLocalDataSource {
    suspend fun getTvShowsFromDB(): List<TvShow>
    suspend fun saveTvShowsFromDB(tvShows: List<TvShow>)
    suspend fun clearAll()
}