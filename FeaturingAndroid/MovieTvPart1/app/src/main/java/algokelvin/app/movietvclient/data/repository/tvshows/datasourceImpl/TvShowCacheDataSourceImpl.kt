package algokelvin.app.movietvclient.data.repository.movies.datasourceImpl

import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.MovieCacheDataSource

class TvShowCacheDataSourceImpl: MovieCacheDataSource {
    private var movieList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesFromCache(movies: List<Movie>) {
        movieList.clear()
        movieList = ArrayList(movies)
    }
}