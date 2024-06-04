package com.anushka.tmdbclient.data.repository.tvshow.datasourceImpl

import com.anushka.tmdbclient.data.api.TMDBService
import com.anushka.tmdbclient.data.model.tvshow.TvShowList
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowRemoteDatasource
import retrofit2.Response

class TvShowRemoteDataSourceImpl(
    private val tmdbService: TMDBService,
    private val apiKey:String
): TvShowRemoteDatasource {
    override suspend fun getTvShows()
            : Response<TvShowList> = tmdbService.getPopularTvShows(apiKey)

}

