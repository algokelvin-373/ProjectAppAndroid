package com.algokelvin.moviecatalog.repository.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.idleresource.EspressoIdlingResource
import com.algokelvin.moviecatalog.model.entity.DataMovie
import com.algokelvin.moviecatalog.model.entity.Movie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Movie_Repository : Movie_Impl {
    private val apiService = MyRetrofit.iniRetrofitMovie()

    override fun getMovieNowPlaying(
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ): LiveData<List<DataMovie>> {
        EspressoIdlingResource.increment()
        val myDataMovieNowPlaying = MutableLiveData<List<DataMovie>>()
        compositeDisposable.add(
            apiService.getDataMovieNowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.data?.take(7) }
            .subscribe(
                {
                    statusResponseMovie.onSuccess(it!!)
                    myDataMovieNowPlaying.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseMovie.onFailed()
                }
            ))
        return myDataMovieNowPlaying
    }

    override fun getDataMovie(
        type: String,
        compositeDisposable: CompositeDisposable,
        statusResponseMovie: StatusResponseMovie
    ) : LiveData<List<DataMovie>> {

        EspressoIdlingResource.increment()
        val myDataMovie : MutableLiveData<List<DataMovie>> = MutableLiveData()
        var observable : Observable<Movie> = apiService.getDataMovieNowPlaying()
        when(type) {
            "now playing" -> observable = apiService.getDataMovieNowPlaying()
            "popular" -> observable = apiService.getDataMoviePopular()
            "top related" -> observable = apiService.getDataMovieTopRated()
            "upcoming" -> observable = apiService.getDataMovieUpComing()
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.data }
            .subscribe(
                {
                    statusResponseMovie.onSuccess(it!!)
                    myDataMovie.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseMovie.onFailed()
                }
            ))

        return myDataMovie
    }

}