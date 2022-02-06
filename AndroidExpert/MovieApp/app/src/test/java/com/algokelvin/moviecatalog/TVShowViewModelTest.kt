package com.algokelvin.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algokelvin.moviecatalog.model.entity.DataTVShow
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import com.algokelvin.moviecatalog.ui.fragment.tvshow.TVShowViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TVShowViewModelTest {

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<DataTVShow>>

    @Mock
    lateinit var tvShowRepository: TVShowRepository

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var tvShowViewModel: TVShowViewModel
    private var apiService = MyRetrofit.iniRetrofitTVShow()
    private val tvShowAiringToday = "airing today"
    private val tvShowOnTheAir = "on the air"
    private val tvShowPopular = "popular"
    private val tvShowTopRelated = "top related"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        tvShowViewModel = TVShowViewModel(tvShowRepository, compositeDisposable)
    }

    @Test
    fun getDataTVShowAiringToday() {
        tvShowViewModel.rqsTVShowAiringToday()

        // For Dummy Data using ApiService
        apiService.getDataTVShowAiringToday()
            .map { it.dataTVShow?.take(7) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getTVShowAiringToday(compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    tvShowViewModel.rqsTVShowAiringToday().observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test // For TVShow : Airing Today
    fun getDataTVShow01() {
        tvShowViewModel.rqsTVShow(tvShowAiringToday)

        apiService.getDataTVShowAiringToday()
            .map { it.dataTVShow }
            .subscribe(
                { dummyData ->
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDataTVShow(tvShowAiringToday, compositeDisposable, capture())
                        firstValue.onSuccess(dummyData!!) // --> "it" is Dummy Data
                    }
                    tvShowViewModel.rqsTVShow(tvShowAiringToday).observeForever(observer)
                    verify(observer).onChanged(dummyData)
                },
                {

                }
            )
    }

    @Test // For TVShow : On The Air
    fun getDataTVShow02() {
        tvShowViewModel.rqsTVShow(tvShowOnTheAir)

        apiService.getDataTVShowOnTheAirToday()
            .map { it.dataTVShow }
            .subscribe(
                { dummyData ->
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDataTVShow(tvShowOnTheAir, compositeDisposable, capture())
                        firstValue.onSuccess(dummyData!!) // --> "it" is Dummy Data
                    }
                    tvShowViewModel.rqsTVShow(tvShowOnTheAir).observeForever(observer)
                    verify(observer).onChanged(dummyData)
                },
                {

                }
            )
    }

    @Test // For TVShow : Popular
    fun getDataTVShow03() {
        tvShowViewModel.rqsTVShow(tvShowPopular)

        apiService.getDataTVShowPopularToday()
            .map { it.dataTVShow }
            .subscribe(
                { dummyData ->
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDataTVShow(tvShowPopular, compositeDisposable, capture())
                        firstValue.onSuccess(dummyData!!) // --> "it" is Dummy Data
                    }
                    tvShowViewModel.rqsTVShow(tvShowPopular).observeForever(observer)
                    verify(observer).onChanged(dummyData)
                },
                {

                }
            )
    }

    @Test // For TVShow : Top Related
    fun getDataTVShow04() {
        tvShowViewModel.rqsTVShow(tvShowTopRelated)

        apiService.getDataTVShowTopRatedToday()
            .map { it.dataTVShow }
            .subscribe(
                { dummyData ->
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDataTVShow(tvShowTopRelated, compositeDisposable, capture())
                        firstValue.onSuccess(dummyData!!) // --> "it" is Dummy Data
                    }
                    tvShowViewModel.rqsTVShow(tvShowTopRelated).observeForever(observer)
                    verify(observer).onChanged(dummyData)
                },
                {

                }
            )
    }
}