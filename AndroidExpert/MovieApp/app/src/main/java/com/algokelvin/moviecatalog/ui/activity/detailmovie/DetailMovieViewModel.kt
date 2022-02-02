package com.algokelvin.moviecatalog.ui.activity.detailmovie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.model.DataCast
import com.algokelvin.moviecatalog.model.DataMovie
import com.algokelvin.moviecatalog.model.DetailMovie
import com.algokelvin.moviecatalog.model.Keyword
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseDetailMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseKeywordMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algokelvin.moviecatalog.util.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(
    private val movieRepository: MovieRepository,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {

    val rspDetailMovie = MutableLiveData<DetailMovie>()
    val rspKeywordMovie = MutableLiveData<ArrayList<Keyword>>()
    val rspCastMovie = MutableLiveData<List<DataCast>>()
    val rspSimilarMovie = MutableLiveData<List<DataMovie>>()
    val rspRecommendationMovie = MutableLiveData<List<DataMovie>>()

    fun rqsDetailMovie(id: Int?) {
        movieRepository.getDetailMovie(id, compositeDisposable, object : StatusResponseDetailMovie {
            override fun onSuccess(data: DetailMovie) = rspDetailMovie.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

    fun rqsKeywordMovie(id: Int?) {
        movieRepository.getKeywordMovie(id, compositeDisposable, object : StatusResponseKeywordMovie {
            override fun onSuccess(dataKeyword: ArrayList<Keyword>) = rspKeywordMovie.postValue(dataKeyword)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

    fun rqsCastMovie(id: Int?) {
        movieRepository.getCastMovie(id, compositeDisposable, object : StatusResponseDataCast {
            override fun onSuccess(data: List<DataCast>) = rspCastMovie.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

    fun rqsSimilarMovie(id: Int?) {
        movieRepository.getSimilarMovie(id, compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = rspSimilarMovie.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }

    fun rqsRecommendationMovie(id: Int?) {
        movieRepository.getRecommendationMovie(id, compositeDisposable, object :StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = rspRecommendationMovie.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
    }
}