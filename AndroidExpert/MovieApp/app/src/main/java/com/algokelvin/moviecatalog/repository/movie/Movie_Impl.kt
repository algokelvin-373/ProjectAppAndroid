package com.algokelvin.moviecatalog.repository.movie

import androidx.lifecycle.LiveData
import com.algokelvin.moviecatalog.model.Resource
import com.algokelvin.moviecatalog.model.entity.DataCast
import com.algokelvin.moviecatalog.model.entity.DataMovie
import com.algokelvin.moviecatalog.model.entity.DetailMovie
import com.algokelvin.moviecatalog.model.entity.Keyword
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseDetailMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseKeywordMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import io.reactivex.disposables.CompositeDisposable

interface Movie_Impl {

    fun getMovieNowPlaying(
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>

    fun getDataMovie(
        type: String,
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>>

}