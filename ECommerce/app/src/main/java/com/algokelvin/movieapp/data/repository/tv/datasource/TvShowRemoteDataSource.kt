package com.algokelvin.movieapp.data.repository.tv.datasource

import com.algokelvin.movieapp.data.model.tv.TvShowList
import retrofit2.Response

interface TvShowRemoteDataSource {
    suspend fun getTvShows(): Response<TvShowList>
}