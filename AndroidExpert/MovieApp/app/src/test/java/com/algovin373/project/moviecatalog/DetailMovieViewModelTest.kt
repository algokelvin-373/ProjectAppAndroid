package com.algovin373.project.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.model.DetailMovie
import com.algovin373.project.moviecatalog.model.Keyword
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.repository.inter.StatusResponseDataCast
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseDetailMovie
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseKeywordMovie
import com.algovin373.project.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algovin373.project.moviecatalog.retrofit.MyRetrofit
import com.algovin373.project.moviecatalog.viewmodel.DetailMovieViewModel
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
        detailMovieViewModel.setDetailMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getDetailMovie(sampleIdMovie.toString())
            .subscribe(
                {
                    argumentCaptor<StatusResponseDetailMovie>().apply {
                        Mockito.verify(movieRepository).getDetailMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    detailMovieViewModel.setDetailMovie(sampleIdMovie).observeForever(observerDetailMovie)
                    verify(observerDetailMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setKeywordMovie() {
        detailMovieViewModel.setKeywordMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getKeywordMovie(sampleIdMovie.toString())
            .map { it.keywordMovie }
            .subscribe(
                {
                    argumentCaptor<StatusResponseKeywordMovie>().apply {
                        Mockito.verify(movieRepository).getKeywordMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.setKeywordMovie(sampleIdMovie).observeForever(observerKeywordMovie)
                    verify(observerKeywordMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setCastMovie() {
        detailMovieViewModel.setCastMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getCastMovie(sampleIdMovie.toString())
            .map { it.dataCast }
            .subscribe(
                {
                    argumentCaptor<StatusResponseDataCast>().apply {
                        Mockito.verify(movieRepository).getCastMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.setCastMovie(sampleIdMovie).observeForever(observerCastMovie)
                    verify(observerCastMovie).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setSimilarMovie() {
        detailMovieViewModel.setSimilarMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getSimilarMovie(sampleIdMovie.toString())
            .map { it.dataMovie?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getSimilarMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.setSimilarMovie(sampleIdMovie).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test
    fun setRecommendationMovie() {
        detailMovieViewModel.setRecommendationMovie(sampleIdMovie)

        // For Dummy Data using ApiService
        apiService.getRecommendtionMovie(sampleIdMovie.toString())
            .map { it.dataMovie?.take(8) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getRecommendationMovie(sampleIdMovie, compositeDisposable, capture())
                        firstValue.onSuccess(it!!)
                    }
                    detailMovieViewModel.setRecommendationMovie(sampleIdMovie).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }
}