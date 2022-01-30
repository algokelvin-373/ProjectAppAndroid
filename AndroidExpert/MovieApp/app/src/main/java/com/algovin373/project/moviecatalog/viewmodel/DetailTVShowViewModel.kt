package com.algovin373.project.moviecatalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.model.DataTVShow
import com.algovin373.project.moviecatalog.model.DetailTVShow
import com.algovin373.project.moviecatalog.repository.TVShowRepository
import com.algovin373.project.moviecatalog.repository.inter.StatusResponseDataCast
import com.algovin373.project.moviecatalog.repository.inter.tvshow.StatusResponseDetailTVShow
import com.algovin373.project.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import io.reactivex.disposables.CompositeDisposable

class DetailTVShowViewModel(private val tvShowRepository: TVShowRepository,
                            private val compositeDisposable: CompositeDisposable) : ViewModel() {
    private val myDetailTVShow = MutableLiveData<DetailTVShow>()
    private val myCastTVShow = MutableLiveData<List<DataCast>>()
    private val mySimilarTVShow = MutableLiveData<List<DataTVShow>>()
    private val myRecommendationTVShow = MutableLiveData<List<DataTVShow>>()

    fun setDetailTVShow(id: Int?) : LiveData<DetailTVShow> {
        tvShowRepository.getDetailTVShow(id, compositeDisposable, object : StatusResponseDetailTVShow {
            override fun onSuccess(data: DetailTVShow) = myDetailTVShow.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myDetailTVShow
    }

    fun setCastTVShow(id: Int?) : LiveData<List<DataCast>> {
        tvShowRepository.getCastTVShow(id, compositeDisposable, object :
            StatusResponseDataCast {
            override fun onSuccess(data: List<DataCast>) = myCastTVShow.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myCastTVShow
    }

    fun setSimilarTVShow(id: Int?) : LiveData<List<DataTVShow>> {
        tvShowRepository.getSimilarTVShow(id, compositeDisposable, object : StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = mySimilarTVShow.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return mySimilarTVShow
    }

    fun setRecommendationTVShow(id: Int?) : LiveData<List<DataTVShow>> {
        tvShowRepository.getRecommendationTVShow(id, compositeDisposable, object :StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = myRecommendationTVShow.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myRecommendationTVShow
    }
}