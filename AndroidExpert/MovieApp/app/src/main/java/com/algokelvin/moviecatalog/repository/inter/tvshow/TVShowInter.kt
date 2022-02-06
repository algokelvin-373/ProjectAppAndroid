package com.algokelvin.moviecatalog.repository.inter.tvshow

import androidx.lifecycle.LiveData
import com.algokelvin.moviecatalog.model.entity.DataCast
import com.algokelvin.moviecatalog.model.entity.DataTVShow
import com.algokelvin.moviecatalog.model.entity.DetailTVShow
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import io.reactivex.disposables.CompositeDisposable

interface TVShowInter {
    fun getTVShowAiringToday(
        disposable: CompositeDisposable,
        statusResponseTVShow: StatusResponseTVShow
    ): LiveData<List<DataTVShow>>

    fun getDataTVShow(
        type: String,
        disposable: CompositeDisposable,
        statusResponseTVShow: StatusResponseTVShow
    ): LiveData<List<DataTVShow>>

    fun getDetailTVShow(
        idTVShow: Int?,
        disposable: CompositeDisposable,
        statusResponseDetailTVShow: StatusResponseDetailTVShow
    ): LiveData<DetailTVShow>

    fun getRecommendationTVShow(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseTVShow: StatusResponseTVShow
    ): LiveData<List<DataTVShow>>

    fun getSimilarTVShow(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseTVShow: StatusResponseTVShow
    ): LiveData<List<DataTVShow>>

    fun getCastTVShow(
        idMovie: Int?,
        disposable: CompositeDisposable,
        statusResponseDataCast: StatusResponseDataCast
    ): LiveData<List<DataCast>>
}