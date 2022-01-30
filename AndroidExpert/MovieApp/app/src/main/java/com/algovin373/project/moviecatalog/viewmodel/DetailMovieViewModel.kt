package com.algovin373.project.moviecatalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.model.DetailMovie
import com.algovin373.project.moviecatalog.model.Keyword
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.repository.inter.StatusResponseDataCast
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseDetailMovie
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseKeywordMovie
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseMovie
import io.reactivex.disposables.CompositeDisposable

class DetailMovieViewModel(private val movieRepository: MovieRepository,
                           private val compositeDisposable: CompositeDisposable) : ViewModel() {
    private val myDetailMovie = MutableLiveData<DetailMovie>()
    private val myKeywordMovie = MutableLiveData<ArrayList<Keyword>>()
    private val myCastMovie = MutableLiveData<List<DataCast>>()
    private val mySimilarMovie = MutableLiveData<List<DataMovie>>()
    private val myRecommendationMovie = MutableLiveData<List<DataMovie>>()

    fun setDetailMovie(id: Int?) : LiveData<DetailMovie> {
        movieRepository.getDetailMovie(id, compositeDisposable, object : StatusResponseDetailMovie {
            override fun onSuccess(data: DetailMovie) = myDetailMovie.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myDetailMovie
    }

    fun setKeywordMovie(id: Int?) : LiveData<ArrayList<Keyword>> {
        movieRepository.getKeywordMovie(id, compositeDisposable, object : StatusResponseKeywordMovie {
            override fun onSuccess(dataKeyword: ArrayList<Keyword>) = myKeywordMovie.postValue(dataKeyword)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myKeywordMovie
    }

    fun setCastMovie(id: Int?) : LiveData<List<DataCast>> {
        movieRepository.getCastMovie(id, compositeDisposable, object :
            StatusResponseDataCast {
            override fun onSuccess(data: List<DataCast>) = myCastMovie.postValue(data)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myCastMovie
    }

    fun setSimilarMovie(id: Int?) : LiveData<List<DataMovie>> {
        movieRepository.getSimilarMovie(id, compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = mySimilarMovie.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return mySimilarMovie
    }

    fun setRecommendationMovie(id: Int?) : LiveData<List<DataMovie>> {
        movieRepository.getRecommendationMovie(id, compositeDisposable, object :StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = myRecommendationMovie.postValue(list)
            override fun onFailed() {
                Log.i("TES", "Failed")
            }
        })
        return myRecommendationMovie
    }
}