package com.algovin373.project.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.model.DataTVShow
import com.algovin373.project.moviecatalog.model.DetailTVShow
import com.algovin373.project.moviecatalog.repository.TVShowRepository
import com.algovin373.project.moviecatalog.repository.inter.StatusResponseDataCast
import com.algovin373.project.moviecatalog.repository.inter.tvshow.StatusResponseDetailTVShow
import com.algovin373.project.moviecatalog.repository.inter.tvshow.StatusResponseTVShow
import com.algovin373.project.moviecatalog.retrofit.MyRetrofit
import com.algovin373.project.moviecatalog.viewmodel.DetailTVShowViewModel
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
        detailTVShowViewModel.setDetailTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getDetailTVShow(sampleIdTVShow.toString())
            .subscribe(
                {
                    argumentCaptor<StatusResponseDetailTVShow>().apply {
                        Mockito.verify(tvShowRepository).getDetailTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    detailTVShowViewModel.setDetailTVShow(sampleIdTVShow).observeForever(observerDetailTVShow)
                    verify(observerDetailTVShow).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setCastTVShow() {
        detailTVShowViewModel.setCastTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getCastTVShow(sampleIdTVShow.toString())
            .map { it.dataCast }
            .subscribe(
                {
                    argumentCaptor<StatusResponseDataCast>().apply {
                        Mockito.verify(tvShowRepository).getCastTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.setCastTVShow(sampleIdTVShow).observeForever(observerCastTVShow)
                    verify(observerCastTVShow).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setSimilarTVShow() {
        detailTVShowViewModel.setSimilarTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getSimilarTVShow(sampleIdTVShow.toString())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getSimilarTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.setSimilarTVShow(sampleIdTVShow).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setRecommendationTVShow() {
        detailTVShowViewModel.setRecommendationTVShow(sampleIdTVShow)

        // For Dummy Data using ApiService
        apiService.getRecommendtionTVShow(sampleIdTVShow.toString())
            .map { it.dataTVShow?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseTVShow>().apply {
                        Mockito.verify(tvShowRepository).getRecommendationTVShow(sampleIdTVShow, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailTVShowViewModel.setRecommendationTVShow(sampleIdTVShow).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }
}