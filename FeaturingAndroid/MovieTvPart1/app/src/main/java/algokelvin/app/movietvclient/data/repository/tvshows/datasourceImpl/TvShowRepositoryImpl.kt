package algokelvin.app.movietvclient.data.repository.movies.datasourceImpl

import algokelvin.app.movietvclient.data.model.tvshows.TvShow
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowCacheDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowLocalDataSource
import algokelvin.app.movietvclient.data.repository.tvshows.datasource.TvShowRemoteDataSource
import algokelvin.app.movietvclient.domain.repository.TvShowRepository
import android.util.Log

class TvShowRepositoryImpl(
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowCacheDataSource: TvShowCacheDataSource
): TvShowRepository {
    override suspend fun getTvShows(): List<TvShow>? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTvShows(): List<TvShow>? {
        val newListOfTvShows = getTvShowFromAPI()
        tvShowLocalDataSource.clearAll()
        tvShowLocalDataSource.saveTvShowsFromDB(newListOfTvShows)
        tvShowCacheDataSource.saveTvShowsFromCache(newListOfTvShows)
        return newListOfTvShows
    }

    suspend fun getTvShowFromAPI(): List<TvShow> {
        lateinit var tvShowsList: List<TvShow>
        try {
            val response = tvShowRemoteDataSource.getTvShows()
            val body = response.body()
            if (body != null) {
                tvShowsList = body.tvShows
            }
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        return tvShowsList
    }

    suspend fun getTvShowsFromDB(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>
        try {
            tvShowList = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        if (tvShowList.isNotEmpty()) {
            return tvShowList
        } else {
            tvShowList = getTvShowFromAPI()
            tvShowLocalDataSource.saveTvShowsFromDB(tvShowList)
        }
        return tvShowList
    }

    suspend fun getTvShowsFromCache(): List<TvShow> {
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList = tvShowCacheDataSource.getTvShowsFromCache()
        } catch (e: Exception) {
            Log.i("AlgoKelvin", e.message.toString())
        }
        if (tvShowsList.isNotEmpty()) {
            return tvShowsList
        } else {
            tvShowsList = getTvShowsFromDB()
            tvShowCacheDataSource.saveTvShowsFromCache(tvShowsList)
        }
        return tvShowsList
    }
}