package com.algokelvin.moviecatalog.ui.activity.detailtv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.model.DataCast
import com.algokelvin.moviecatalog.model.DataTVShow
import com.algokelvin.moviecatalog.model.DetailTVShow
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseDetailTVShow
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algokelvin.moviecatalog.util.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class DetailTVShowViewModel(
    private val tvShowRepository: TVShowRepository,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {

    val rspDetailTVShow = MutableLiveData<DetailTVShow>()
    val rspCastTVShow = MutableLiveData<List<DataCast>>()
    val rspSimilarTVShow = MutableLiveData<List<DataTVShow>>()
    val rspRecommendationTVShow = MutableLiveData<List<DataTVShow>>()

    fun rqsDetailTVShow(id: Int?) : LiveData<DetailTVShow> {
        tvShowRepository.getDetailTVShow(id, compositeDisposable, object : StatusResponseDetailTVShow {
            override fun onSuccess(data: DetailTVShow) = rspDetailTVShow.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return rspDetailTVShow
    }

    fun rqsCastTVShow(id: Int?) : LiveData<List<DataCast>> {
        tvShowRepository.getCastTVShow(id, compositeDisposable, object : StatusResponseDataCast {
            override fun onSuccess(data: List<DataCast>) = rspCastTVShow.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return rspCastTVShow
    }

    fun rqsSimilarTVShow(id: Int?) : LiveData<List<DataTVShow>> {
        tvShowRepository.getSimilarTVShow(id, compositeDisposable, object : StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = rspSimilarTVShow.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return rspSimilarTVShow
    }

    fun rqsRecommendationTVShow(id: Int?) : LiveData<List<DataTVShow>> {
        tvShowRepository.getRecommendationTVShow(id, compositeDisposable, object :StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = rspRecommendationTVShow.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return rspRecommendationTVShow
    }
}