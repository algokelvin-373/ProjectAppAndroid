package com.anushka.tmdbclient.data.repository.tvshow

import android.util.Log
import com.anushka.tmdbclient.data.model.tvshow.TvShow
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowCacheDataSource
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowLocalDataSource
import com.anushka.tmdbclient.data.repository.tvshow.datasource.TvShowRemoteDatasource
import com.anushka.tmdbclient.domain.repository.TvShowRepository
import java.lang.Exception

class TvShowRepositoryImpl(
    private val tvShowRemoteDatasource: TvShowRemoteDatasource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowCacheDataSource: TvShowCacheDataSource
) : TvShowRepository {
    override suspend fun getTvShows(): List<TvShow>? {
       return getTvShowsFromCache()
    }

    override suspend fun updateTvShows(): List<TvShow>? {
        val newListOfTvShows = getTvShowsFromAPI()
        tvShowLocalDataSource.clearAll()
        tvShowLocalDataSource.saveTvShowsToDB(newListOfTvShows)
        tvShowCacheDataSource.saveTvShowsToCache(newListOfTvShows)
        return newListOfTvShows
    }

    suspend fun getTvShowsFromAPI(): List<TvShow> {
        lateinit var tvShowList: List<TvShow>
        try {
            val response = tvShowRemoteDatasource.getTvShows()
            val body = response.body()
            if(body!=null){
                tvShowList = body.tvShows
            }
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        return tvShowList
    }

    suspend fun getTvShowsFromDB():List<TvShow>{
        lateinit var tvShowsList: List<TvShow>
        try {
           tvShowsList = tvShowLocalDataSource.getTvShowsFromDB()
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        if(tvShowsList.size>0){
            return tvShowsList
        }else{
            tvShowsList=getTvShowsFromAPI()
            tvShowLocalDataSource.saveTvShowsToDB(tvShowsList)
        }

        return tvShowsList
    }

    suspend fun getTvShowsFromCache():List<TvShow>{
        lateinit var tvShowsList: List<TvShow>
        try {
            tvShowsList =tvShowCacheDataSource.getTvShowsFromCache()
        } catch (exception: Exception) {
            Log.i("MyTag", exception.message.toString())
        }
        if(tvShowsList.size>0){
            return tvShowsList
        }else{
            tvShowsList=getTvShowsFromDB()
            tvShowCacheDataSource.saveTvShowsToCache(tvShowsList)
        }

        return tvShowsList
    }







}