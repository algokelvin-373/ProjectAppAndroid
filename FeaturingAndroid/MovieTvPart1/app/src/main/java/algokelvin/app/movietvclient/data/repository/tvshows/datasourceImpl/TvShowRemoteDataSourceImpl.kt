package algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl

import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.model.tvshows.TvShowList
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowRemoteDataSource
import retrofit2.Response

class TvShowRemoteDataSourceImpl(
    private val movieTvServices: MovieTvServices,
    private val apiKey: String
): TvShowRemoteDataSource {
    override suspend fun getTvShows(): Response<TvShowList> {
        return movieTvServices.getPopularTvShows(apiKey)
    }
}