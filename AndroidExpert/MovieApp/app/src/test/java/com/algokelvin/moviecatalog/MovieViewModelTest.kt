package com.algokelvin.moviecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.algokelvin.moviecatalog.model.entity.DataMovie
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.repository.inter.movie.StatusResponseMovie
import com.algokelvin.moviecatalog.retrofit.MyRetrofit
import com.algokelvin.moviecatalog.ui.fragment.movie.MovieViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieViewModelTest {
    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<DataMovie>>

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var movieViewModel: MovieViewModel
    private var apiService = MyRetrofit.iniRetrofitMovie()
    private val movieNowPlaying = "now playing"
    private val moviePopular = "popular"
    private val movieTopRelated = "top related"
    private val movieUpcoming = "upcoming"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(movieRepository, compositeDisposable)
    }

    @Test
    fun getDataMovieNowPlaying() {
        movieViewModel.rqsMovieNowPlaying()

        // For Dummy Data using ApiService
        apiService.getDataMovieNowPlaying()
            .map { it.data?.take(7) }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getMovieNowPlaying(compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    movieViewModel.rqsMovieNowPlaying().observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test // For Movie : Now Playing
    fun getDataMovie01() {
        movieViewModel.rqsMovie(movieNowPlaying)

        apiService.getDataMovieNowPlaying()
            .map { it.data }
            .subscribe(
                { dummyData ->
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getDataMovie(movieNowPlaying, compositeDisposable, capture())
                        firstValue.onSuccess(dummyData!!) // --> "it" is Dummy Data
                    }
                    movieViewModel.rqsMovie(movieNowPlaying).observeForever(observer)
                    verify(observer).onChanged(dummyData)
                },
                {

                }
            )
    }

    @Test // For Movie : Popular
    fun getDataMovie02() {
        movieViewModel.rqsMovie(moviePopular)

        apiService.getDataMoviePopular()
            .map { it.data }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getDataMovie(moviePopular, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    movieViewModel.rqsMovie(moviePopular).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test // For Movie : Top Related
    fun getDataMovie03() {
        movieViewModel.rqsMovie(movieTopRelated)

        apiService.getDataMovieTopRated()
            .map { it.data }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getDataMovie(movieTopRelated, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    movieViewModel.rqsMovie(movieTopRelated).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }

    @Test // For Movie : Upcoming
    fun getDataMovie04() {
        movieViewModel.rqsMovie(movieUpcoming)

        apiService.getDataMovieUpComing()
            .map { it.data }
            .subscribe(
                {
                    argumentCaptor<StatusResponseMovie>().apply {
                        Mockito.verify(movieRepository).getDataMovie(movieUpcoming, compositeDisposable, capture())
                        firstValue.onSuccess(it!!) // --> "it" is Dummy Data
                    }
                    movieViewModel.rqsMovie(movieUpcoming).observeForever(observer)
                    verify(observer).onChanged(it)
                },
                {

                }
            )
    }
}