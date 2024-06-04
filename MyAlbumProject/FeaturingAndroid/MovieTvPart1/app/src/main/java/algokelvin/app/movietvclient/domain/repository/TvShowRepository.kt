package algokelvin.app.movietvclient.domain.repository

import algokelvin.app.movietvclient.data.model.tvshows.TvShow

interface TvShowRepository {
    suspend fun getTvShows(): List<TvShow>?
    suspend fun updateTvShows(): List<TvShow>?
}