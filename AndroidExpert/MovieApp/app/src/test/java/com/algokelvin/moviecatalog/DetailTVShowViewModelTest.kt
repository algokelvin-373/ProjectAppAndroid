package com.algokelvin.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algokelvin.moviecatalog.model.DataCast
import com.algokelvin.moviecatalog.model.DataTVShow
import com.algokelvin.moviecatalog.model.DetailTVShow
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseDetailTVShow
import com.algokelvin.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import com.algokelvin.moviecatalog.ui.activity.detailtv.DetailTVShowViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailTVShowViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerDetailTVShow: Observer<DetailTVShow>

    @Mock
    lateinit var observerCastTVShow: Observer<List<DataCast>>

    @Mock
    lateinit var observer: Observer<List<DataTVShow>>

    @Mock
    lateinit var tvShowRepository: TVShowRepository

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var detailTVShowViewModel: DetailTVShowViewModel
    private var apiService = MyRetrofit.iniRetrofitTVShow()
    private val sampleIdTVShow = 88046

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailTVShowViewModel = DetailTVShowViewModel(tvShowRepository, compositeDisposable)
    }

    @Test
    fun setDetailTVShow() {
        detailTVShowViewModel.rqsDetailTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getDetailTVShow(sampleIdTVShow.toString())
            .subscribe(
                {
                    argumentCaptor<StatusResponseDetailTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDetailTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    detailTVShowViewModel.rqsDetailTVShow(sampleIdTVShow).observeForever(observerDetailTVShow)
                    verify(observerDetailTVShow).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setCastTVShow() {
        detailTVShowViewModel.rqsCastTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getCastTVShow(sampleIdTVShow.toString())
            .map { it.dataCast }
            .subscribe(
                {
                    argumentCaptor<StatusResponseDataCast>().apply {
                        Mockito.verify(tvShowRepository).getCastTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.rqsCastTVShow(sampleIdTVShow).observeForever(observerCastTVShow)
                    verify(observerCastTVShow).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setSimilarTVShow() {
        detailTVShowViewModel.rqsSimilarTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getSimilarTVShow(sampleIdTVShow.toString())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getSimilarTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.rqsSimilarTVShow(sampleIdTVShow).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setRecommendationTVShow() {
        detailTVShowViewModel.rqsRecommendationTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getRecommendtionTVShow(sampleIdTVShow.toString())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getRecommendationTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.rqsRecommendationTVShow(sampleIdTVShow).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }
}