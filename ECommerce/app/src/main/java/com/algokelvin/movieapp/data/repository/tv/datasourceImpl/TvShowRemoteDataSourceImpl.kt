package com.algokelvin.movieapp.data.repository.tv.datasourceImpl

import com.algokelvin.movieapp.data.api.ProductApiService
import com.algokelvin.movieapp.data.model.tv.TvShowList
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import retrofit2.Response

class TvShowRemoteDataSourceImpl(
    private val service: ProductApiService,
    private val apiKey: String,
): TvShowRemoteDataSource {
    override suspend fun getTvShows(): Response<TvShowList> = service.getTvShowListPopular(apiKey)
}