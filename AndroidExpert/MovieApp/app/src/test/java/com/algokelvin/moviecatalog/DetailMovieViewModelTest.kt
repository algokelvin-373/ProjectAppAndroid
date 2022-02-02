package com.algokelvin.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algokelvin.moviecatalog.model.DataCast
import com.algokelvin.moviecatalog.model.DataMovie
import com.algokelvin.moviecatalog.model.DetailMovie
import com.algokelvin.moviecatalog.model.Keyword
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.repository.inter.StatusResponseDataCast
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseDetailMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseKeywordMovie
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import com.algokelvin.moviecatalog.ui.activity.detailmovie.DetailMovieViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMovieViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerDetailMovie: Observer<DetailMovie>

    @Mock
    lateinit var observerKeywordMovie: Observer<ArrayList<Keyword>>

    @Mock
    lateinit var observerCastMovie: Observer<List<DataCast>>

    @Mock
    lateinit var observer: Observer<List<DataMovie>>

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private var apiService = MyRetrofit.iniRetrofitMovie()

    private val sampleIdMovie = 384018

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMovieViewModel = DetailMovieViewModel(movieRepository, compositeDisposable)
    }

    @Test
    fun setDetailMovie() {
        detailMovieViewModel.rqsDetailMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getDetailMovie(sampleIdMovie.toString())
            .subscribe(
                {
                    argumentCaptor<StatusResponseDetailMovie>().apply {
                        Mockito.verify(movieRepository).getDetailMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    detailMovieViewModel.rqsDetailMovie(sampleIdMovie).observeForever(observerDetailMovie)
                    verify(observerDetailMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setKeywordMovie() {
        detailMovieViewModel.rqsKeywordMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getKeywordMovie(sampleIdMovie.toString())
            .map { it.keywordMovie }
            .subscribe(
                {
                    argumentCaptor<StatusResponseKeywordMovie>().apply {
                        Mockito.verify(movieRepository).getKeywordMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.rqsKeywordMovie(sampleIdMovie).observeForever(observerKeywordMovie)
                    verify(observerKeywordMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setCastMovie() {
        detailMovieViewModel.rqsCastMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getCastMovie(sampleIdMovie.toString())
            .map { it.dataCast }
            .subscribe(
                {
                    argumentCaptor<StatusResponseDataCast>().apply {
                        Mockito.verify(movieRepository).getCastMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.rqsCastMovie(sampleIdMovie).observeForever(observerCastMovie)
                    verify(observerCastMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setSimilarMovie() {
        detailMovieViewModel.rqsSimilarMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getSimilarMovie(sampleIdMovie.toString())
            .map { it.dataMovie?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getSimilarMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.rqsSimilarMovie(sampleIdMovie).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setRecommendationMovie() {
        detailMovieViewModel.rqsRecommendationMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getRecommendtionMovie(sampleIdMovie.toString())
            .map { it.dataMovie?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getRecommendationMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.rqsRecommendationMovie(sampleIdMovie).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }
}