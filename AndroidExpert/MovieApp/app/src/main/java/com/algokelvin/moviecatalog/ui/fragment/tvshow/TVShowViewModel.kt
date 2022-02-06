package com.algokelvin.moviecatalog.ui.fragment.tvshow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.model.entity.DataTVShow
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algokelvin.moviecatalog.util.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class TVShowViewModel(
    private val tvShowRepository: TVShowRepository,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {

    val rspTVShow = MutableLiveData<List<DataTVShow>>()
    val rspTVShowAiringToday = MutableLiveData<List<DataTVShow>>()

    fun rqsTVShowAiringToday() {
        tvShowRepository.getTVShowAiringToday(compositeDisposable, object : StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = rspTVShowAiringToday.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

    fun rqsTVShow(type: String) {
        tvShowRepository.getDataTVShow(type, compositeDisposable, object : StatusResponseTVShow {
            override fun onSuccess(list: List<DataTVShow>) = rspTVShow.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

}