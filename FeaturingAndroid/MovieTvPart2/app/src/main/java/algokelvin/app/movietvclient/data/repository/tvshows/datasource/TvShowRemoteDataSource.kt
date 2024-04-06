package algokelvin.app.movietvclient.data.repository.tvshows.datasource

import algokelvin.app.movietvclient.data.model.tvshows.TvShowList
import retrofit2.Response

interface TvShowRemoteDataSource {
    suspend fun getTvShows(): Response<TvShowList>
}