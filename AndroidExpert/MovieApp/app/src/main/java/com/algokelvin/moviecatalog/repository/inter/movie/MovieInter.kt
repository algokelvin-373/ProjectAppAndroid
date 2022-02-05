package com.algokelvin.moviecatalog.repository.inter.movie

import androidx.lifecycle.LiveData
import com.algokelvin.moviecatalog.model.DataCast
import com.algokelvin.moviecatalog.model.DataMovie
import com.algokelvin.moviecatalog.model.DetailMovie
import com.algokelvin.moviecatalog.model.Keyword
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import io.reactivex.disposables.CompositeDisposable

interface MovieInter {
    fun getMovieNowPlaying(
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>

    fun getDataMovie(
        type: String,
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>

    fun getDetailMovie(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseDetailMovie: StatusResponseDetailMovie
    ): LiveData<DetailMovie>

    fun getKeywordMovie(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseKeywordMovie: StatusResponseKeywordMovie
    ): LiveData<ArrayList<Keyword>>

    fun getCastMovie(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseDataCast: StatusResponseDataCast
    ): LiveData<List<DataCast>>

    fun getSimilarMovie(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>

    fun getRecommendationMovie(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>
}