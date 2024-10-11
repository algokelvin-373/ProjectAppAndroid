package com.algokelvin.movieapp.data.repository.tv

import android.util.Log
import com.algokelvin.movieapp.data.model.tv.TvShow
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowCacheDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowLocalDataSource
import com.algokelvin.movieapp.data.repository.tv.datasource.TvShowRemoteDataSource
import com.algokelvin.movieapp.domain.repository.TvShowRepository

class TvShowRepositoryImpl(
    private val remote: TvShowRemoteDataSource,
    private val local: TvShowLocalDataSource,
    private val cache: TvShowCacheDataSource,
): TvShowRepository {
    override suspend fun getTvShows(): List<TvShow> = getTvShowFromCache()

    override suspend fun updateTvShows(): List<TvShow> {
        val newListOfTvShows = getTvShowsFromAPI()
        local.clearAll()
        local.saveTvShowsToDB(newListOfTvShows)
        cache.saveTvShowsToCache(newListOfTvShows)
        return newListOfTvShows
    }

    suspend fun getTvShowsFromAPI(): List<TvShow> {
        lateinit var tvShowsList: List<TvShow>

        try {
            val response = remote.getTvShows()
            val body = response.body()
            if (body != null) {
                tvShowsList = body.results
            }
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        return tvShowsList
    }

    suspend fun getTvShowsFromDB(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>

        try {
            tvShowList = local.getTvShowsFromDB()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (tvShowList.isNotEmpty()) {
            return tvShowList
        } else {
            tvShowList = getTvShowsFromAPI()
            local.saveTvShowsToDB(tvShowList)
        }

        return tvShowList
    }

    suspend fun getTvShowFromCache(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>

        try {
            tvShowList = cache.getTvShowsFromCache()
        } catch (e: Exception) {
            Log.i("ALGOKELVIN", e.message.toString())
        }

        if (tvShowList.isNotEmpty()) {
            return tvShowList
        } else {
            tvShowList = getTvShowsFromDB()
            cache.saveTvShowsToCache(tvShowList)
        }

        return tvShowList
    }
}