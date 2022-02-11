package com.algokelvin.moviecatalog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.algokelvin.moviecatalog.idleresource.EspressoIdlingResource
import com.algokelvin.moviecatalog.model.entity.DataCast
import com.algokelvin.moviecatalog.model.entity.DataTVShow
import com.algokelvin.moviecatalog.model.entity.DetailTVShow
import com.algokelvin.moviecatalog.model.entity.TVShow
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseDetailTVShow
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algokelvin.moviecatalog.repository.inter.tvshow.TVShowInter
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TVShowRepository : TVShowInter {
    private val apiService = MyRetrofit.iniRetrofitTVShow()

    override fun getTVShowAiringToday(disposable: CompositeDisposable, statusResponseTVShow: StatusResponseTVShow): LiveData<List<DataTVShow>> {
        EspressoIdlingResource.increment()
        val myTVShowAiringToday = MutableLiveData<List<DataTVShow>>()
        disposable.add(
            apiService.getDataTVShowAiringToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.dataTVShow?.take(5) }
                .subscribe(
                    {
                        statusResponseTVShow.onSuccess(it!!)
                        myTVShowAiringToday.postValue(it)
                        EspressoIdlingResource.decrement()
                    },
                    {
                        statusResponseTVShow.onFailed()
                    }
                ))
        return myTVShowAiringToday
    }

    override fun getDataTVShow(type: String, disposable: CompositeDisposable, statusResponseTVShow: StatusResponseTVShow): LiveData<List<DataTVShow>> {
        EspressoIdlingResource.increment()
        val myDataTVShow : MutableLiveData<List<DataTVShow>> = MutableLiveData()
        var observable : Observable<TVShow> = apiService.getDataTVShowAiringToday()
        when(type) {
            "airing today" -> observable = apiService.getDataTVShowAiringToday()
            "popular" -> observable = apiService.getDataTVShowPopularToday()
            "top related" -> observable = apiService.getDataTVShowTopRatedToday()
            "on the air" -> observable = apiService.getDataTVShowOnTheAirToday()
        }
        disposable.add(observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.dataTVShow }
            .subscribe(
                {
                    statusResponseTVShow.onSuccess(it!!)
                    myDataTVShow.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseTVShow.onFailed()
                }
            ))
        return myDataTVShow
    }

    override fun getDetailTVShow(idTVShow: Int?, disposable: CompositeDisposable, statusResponseDetailTVShow: StatusResponseDetailTVShow): LiveData<DetailTVShow> {
        EspressoIdlingResource.increment()
        val myDetailTVShow : MutableLiveData<DetailTVShow> = MutableLiveData()
        disposable.add(
            apiService.getDetailTVShow(idTVShow.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        statusResponseDetailTVShow.onSuccess(it!!)
                        myDetailTVShow.postValue(it)
                        EspressoIdlingResource.decrement()
                    },
                    {
                        statusResponseDetailTVShow.onFailed()
                    }
                )
        )
        return myDetailTVShow
    }

    override fun getCastTVShow(idMovie: Int?, disposable: CompositeDisposable, statusResponseDataCast: StatusResponseDataCast): LiveData<List<DataCast>> {
        EspressoIdlingResource.increment()
        val myCastTVShow: MutableLiveData<List<DataCast>> = MutableLiveData()
        disposable.add(apiService.getCastTVShow(idMovie.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.dataCast }
            .subscribe(
                {
                    statusResponseDataCast.onSuccess(it!!)
                    myCastTVShow.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseDataCast.onFailed()
                }
            )
        )
        return myCastTVShow
    }

    override fun getSimilarTVShow(idMovie: Int?, disposable: CompositeDisposable, statusResponseTVShow: StatusResponseTVShow): LiveData<List<DataTVShow>> {
        EspressoIdlingResource.increment()
        val mySimilarTVShow: MutableLiveData<List<DataTVShow>> = MutableLiveData()
        disposable.add(apiService.getSimilarTVShow(idMovie.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    statusResponseTVShow.onSuccess(it!!)
                    mySimilarTVShow.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseTVShow.onFailed()
                }
            )
        )
        return mySimilarTVShow
    }

    override fun getRecommendationTVShow(idMovie: Int?, disposable: CompositeDisposable, statusResponseTVShow: StatusResponseTVShow): LiveData<List<DataTVShow>> {
        EspressoIdlingResource.increment()
        val myRecommendationTVShow: MutableLiveData<List<DataTVShow>> = MutableLiveData()
        disposable.add(apiService.getRecommendtionTVShow(idMovie.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    statusResponseTVShow.onSuccess(it!!)
                    myRecommendationTVShow.postValue(it)
                    EspressoIdlingResource.decrement()
                },
                {
                    statusResponseTVShow.onFailed()
                }
            )
        )
        return myRecommendationTVShow
    }

}