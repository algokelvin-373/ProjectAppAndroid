package com.algokelvin.movieapp.data.repository.tv.datasource

import com.algokelvin.movieapp.data.model.tv.TvShow

interface TvShowCacheDataSource {
    suspend fun getTvShowsFromCache(): List<TvShow>
    suspend fun saveTvShowsToCache(tvShows: List<TvShow>)
}