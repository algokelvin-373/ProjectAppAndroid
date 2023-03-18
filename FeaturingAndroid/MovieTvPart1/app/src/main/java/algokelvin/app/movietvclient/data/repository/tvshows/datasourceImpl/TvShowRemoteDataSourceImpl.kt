package algokelvin.app.movietvclient.data.repository.movies.datasourceImpl

import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.model.movies.MovieList
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowRemoteDataSource
import retrofit2.Response

class TvShowRemoteDataSourceImpl(
    private val movieTvServices: MovieTvServices,
    private val apiKey: String
): TvShowRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> {
        return movieTvServices.getPopularMovies(apiKey)
    }
}