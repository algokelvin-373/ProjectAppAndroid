package algokelvin.app.movietvclient.data.repository.movies.datasourceImpl

import algokelvin.app.movietvclient.data.api.MovieTvServices
import algokelvin.app.movietvclient.data.model.movies.MovieList
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val movieTvServices: MovieTvServices,
    private val apiKey: String
): MovieRemoteDataSource {
    override suspend fun getMovies(): Response<MovieList> {
        return movieTvServices.getPopularMovies(apiKey)
    }
}