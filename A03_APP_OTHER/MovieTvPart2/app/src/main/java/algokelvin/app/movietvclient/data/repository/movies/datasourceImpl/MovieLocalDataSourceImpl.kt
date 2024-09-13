package algokelvin.app.movietvclient.data.repository.movies.datasourceImpl

import algokelvin.app.movietvclient.data.db.MovieDao
import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.data.repository.movies.datasource.MovieLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(private val movieTvDao: MovieDao): MovieLocalDataSource {
    override suspend fun getMoviesFromDB(): List<Movie> = movieTvDao.getMovies()

    override suspend fun saveMoviesFromDB(movies: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieTvDao.saveMovies(movies)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            movieTvDao.deleteAllMovies()
        }
    }
}