package com.algokelvin.moviecatalog.ui.activity.detail.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.algokelvin.moviecatalog.databinding.ActivityDetailMovieBinding
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.ui.activity.detail.InitViewDetailMovie
import io.reactivex.disposables.CompositeDisposable

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var initViewDetailMovie: InitViewDetailMovie

    private val detailMovieViewModelFactory by lazy {
        DetailMovieViewModelFactory(MovieRepository(), CompositeDisposable())
    }

    private val detailMovieViewModel by lazy {
        ViewModelProviders.of(this, detailMovieViewModelFactory)
            .get(DetailMovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewDetailMovie = InitViewDetailMovie(this, binding)
        val id = intent.getIntExtra("ID", 0)

        setDetail(id)
        setKeywordMovie(id)
        setCast(id)
        setSimilarMovie(id)
        setRecommendation(id)

    }

    private fun setDetail(id: Int) {
        detailMovieViewModel.rqsDetailMovie(id)
        detailMovieViewModel.rspDetailMovie.observe(this, Observer {
            initViewDetailMovie.setDetail(it)
        })
    }

    private fun setKeywordMovie(id: Int) {
        detailMovieViewModel.rqsKeywordMovie(id)
        detailMovieViewModel.rspKeywordMovie.observe(this, Observer {
            initViewDetailMovie.setKeyword(it)
        })
    }

    private fun setCast(id: Int) {
        detailMovieViewModel.rqsCastMovie(id)
        detailMovieViewModel.rspCastMovie.observe(this, Observer {
            initViewDetailMovie.setDataCast(it)
        })
    }

    private fun setSimilarMovie(id: Int) {
        detailMovieViewModel.rqsSimilarMovie(id)
        detailMovieViewModel.rspSimilarMovie.observe(this, Observer { data ->
            initViewDetailMovie.setSimilar(data)
        })
    }

    private fun setRecommendation(id: Int) {
        detailMovieViewModel.rqsRecommendationMovie(id)
        detailMovieViewModel.rspRecommendationMovie.observe(this, Observer { data ->
            initViewDetailMovie.setRecommendation(data)
        })
    }

}
