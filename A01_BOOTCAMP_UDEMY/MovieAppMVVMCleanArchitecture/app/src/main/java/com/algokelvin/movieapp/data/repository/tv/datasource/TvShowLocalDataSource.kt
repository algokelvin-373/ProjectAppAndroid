package com.algokelvin.movieapp.data.repository.tv.datasource

import com.algokelvin.movieapp.data.model.tv.TvShow

interface TvShowLocalDataSource {
    suspend fun getTvShowsFromDB(): List<TvShow>
    suspend fun saveTvShowsToDB(tvShows: List<TvShow>)
    suspend fun clearAll()
}