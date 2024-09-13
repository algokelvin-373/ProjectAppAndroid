package algokelvin.app.movietvclient.data.repository.movies.datasource

import algokelvin.app.movietvclient.data.model.movies.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieList>
}