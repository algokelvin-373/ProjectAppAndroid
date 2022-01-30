package com.algovin373.project.moviecatalog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseMovie
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieRepository: MovieRepository,
                     private val compositeDisposable: CompositeDisposable) : ViewModel() {
    private val myData = MutableLiveData<List<DataMovie>>()
    private val myDataMovieNowPlaying = MutableLiveData<List<DataMovie>>()

    fun getDataMovieNowPlaying() : LiveData<List<DataMovie>> {
        movieRepository.getMovieNowPlaying(compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = myDataMovieNowPlaying.postValue(list)
            override fun onFailed() {
                Log.i("TES","Failed")
            }
        })
        return myDataMovieNowPlaying
    }

    fun getDataMovie(type: String) : LiveData<List<DataMovie>> {
        movieRepository.getDataMovie(type, compositeDisposable, object : StatusResponseMovie {
            override fun onSuccess(list: List<DataMovie>) = myData.postValue(list)
            override fun onFailed() {
                Log.i("TES","Failed")
            }
        })
        return myData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}