package com.algokelvin.moviecatalog.ui.fragment.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.model.entity.DataMovie
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algokelvin.moviecatalog.util.BaseViewModel
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(
    private val movieRepository: MovieRepository,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {

    val rspMovie = MutableLiveData<List<DataMovie>>()
    val rspMovieNowPlaying = MutableLiveData<List<DataMovie>>()

    fun rqsMovieNowPlaying() {
        movieRepository.getMovieNowPlaying(compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = rspMovieNowPlaying.postValue(list)
            override fun onFailed() {
                Log.i("TES","Failed")
            }
        })
    }

    fun rqsMovie(type: String) {
        movieRepository.getDataMovie(type, compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = rspMovie.postValue(list)
            override fun onFailed() {
                Log.i("TES","Failed")
            }
        })
    }

}