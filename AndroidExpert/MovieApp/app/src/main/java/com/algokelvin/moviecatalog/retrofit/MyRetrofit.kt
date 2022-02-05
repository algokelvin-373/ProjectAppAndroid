package com.algokelvin.moviecatalog.retrofit

import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.retrofit.movie.RestApiMovie
import com.algokelvin.moviecatalog.retrofit.tvshow.RestApiTVShow
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofit {

    fun iniRetrofitMovie(): RestApiMovie {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_MOVIE_CATALOG)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestApiMovie::class.java)
    }

    fun iniRetrofitTVShow(): RestApiTVShow {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_MOVIE_CATALOG)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestApiTVShow::class.java)
    }
}