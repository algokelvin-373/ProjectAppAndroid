package algokelvin.app.movietvclient.data.repository.tvshows.datasourceImpl

import algokelvin.app.movietvclient.data.db.MovieDao
import algokelvin.app.movietvclient.data.db.TvShowDao
import algokelvin.app.movietvclient.data.model.movies.Movie
import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowLocalDataSourceImpl(private val tvShowDao: TvShowDao): TvShowLocalDataSource {
    override suspend fun getTvShowsFromDB(): List<TvShow> = tvShowDao.getTvShows()

    override suspend fun saveTvShowsFromDB(tvShows: List<TvShow>) {
        CoroutineScope(Dispatchers.IO).launch {
            tvShowDao.saveTvShows(tvShows)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            tvShowDao.deleteTvShows()
        }
    }
}