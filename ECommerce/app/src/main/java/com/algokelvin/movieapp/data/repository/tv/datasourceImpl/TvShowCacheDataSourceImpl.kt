package com.algokelvin.movieapp.data.repository.tv.datasourceImpl

import com.algokelvin.movieapp.data.model.tv.TvShow
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource

class TvShowCacheDataSourceImpl: TvShowCacheDataSource {
    private var tvShowsList = ArrayList<TvShow>()

    override suspend fun getTvShowsFromCache(): List<TvShow> {
        return tvShowsList
    }

    override suspend fun saveTvShowsToCache(tvShows: List<TvShow>) {
        tvShowsList.clear()
        tvShowsList = ArrayList(tvShows)
    }
}