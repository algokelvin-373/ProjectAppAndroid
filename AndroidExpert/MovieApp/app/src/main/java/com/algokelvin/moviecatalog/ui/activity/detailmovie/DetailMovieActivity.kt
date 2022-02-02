package com.algokelvin.moviecatalog.ui.activity.detailmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.ActivityDetailMovieBinding
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethod.glideImg
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.item_cast.view.*
import kotlinx.android.synthetic.main.item_catalog_other.view.*
import org.jetbrains.anko.startActivity

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding

    private val detailMovieViewModelFactory by lazy {
        DetailMovieViewModelFactory(movieRepository = MovieRepository(), compositeDisposable = CompositeDisposable())
    }

    private val detailMovieViewModel by lazy {
        ViewModelProviders.of(this, detailMovieViewModelFactory).get(DetailMovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("ID", 0)

        setDetail(id)
        setKeywordMovie(id)
        setCast(id)
        setSimilarMovie(id)
        setRecommendation(id)

        btn_back_to_menu.setOnClickListener { finish() }
    }

    private fun setDetail(id: Int) {
        detailMovieViewModel.rqsDetailMovie(id)
        detailMovieViewModel.rspDetailMovie.observe(this, Observer {
            binding.apply {
                glideImg("${BuildConfig.URL_POSTER}${it.backgroundMovie}", imagePosterCatalogMovie)
                glideImg("${BuildConfig.URL_IMAGE}${it.posterMovie}", imageMovieCatalog)
                titleCatalogMovie.text = it.titleMovie
                dateReleaseCatalogMovie.text = it.releaseDateMovie
                statusReleaseCatalogMovie.text = it.statusMovie
                runtimeReleaseCatalogMovie.text = it.runtimeMovie.toString()
                voteAverageReleaseCatalogMovie.text = it.voteAverageMovie.toString()
                voteCountReleaseCatalogMovie.text = it.voteCountMovie.toString()
                overviewCatalogMovie.text = it.overviewMovie
            }
        })
    }

    private fun setKeywordMovie(id: Int) {
        detailMovieViewModel.rqsKeywordMovie(id)
        detailMovieViewModel.rspKeywordMovie.observe(this, Observer {
            var keyword = ""
            for (i in 0 until it.size) {
                keyword += if (i == it.size-1) "${it[i].keyword}" else "${it[i].keyword}, "
            }
            keyword_catalog_movie.text = keyword
        })
    }

    private fun setCast(id: Int) {
        detailMovieViewModel.rqsCastMovie(id)
        detailMovieViewModel.rspCastMovie.observe(this, Observer {
            binding.rvCastMovie.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(it.size, R.layout.item_cast) { v, i ->
                    val urlImage = "${BuildConfig.URL_IMAGE}${it[i].posterCast}"
                    glideImg(urlImage, v.image_cast)
                    v.name_cast.text = it[i].nameCast
                    v.character_cast.text = it[i].characterCast
                }
            }
        })
    }

    private fun setSimilarMovie(id: Int) {
        detailMovieViewModel.rqsSimilarMovie(id)
        detailMovieViewModel.rspSimilarMovie.observe(this, Observer { data ->
            binding.rvSimilarMovie.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                    val urlImage = "${BuildConfig.URL_POSTER}${data[i].backgroundDateMovie}"
                    Glide.with(context).load(urlImage).into(v.image_other_movie)
                    v.title.text = data[i].titleMovie
                    v.date_release.text = data[i].releaseDateMovie
                    v.setOnClickListener {
                        startActivity<DetailMovieActivity>("ID" to data[i].idMovie)
                    }
                }
            }
        })
    }

    private fun setRecommendation(id: Int) {
        detailMovieViewModel.rqsRecommendationMovie(id)
        detailMovieViewModel.rspRecommendationMovie.observe(this, Observer { data ->
            binding.rvRecommendationMovie.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                    val urlImage = "${BuildConfig.URL_POSTER}${data[i].backgroundDateMovie}"
                    Glide.with(context).load(urlImage).into(v.image_other_movie)
                    v.title.text = data[i].titleMovie
                    v.date_release.text = data[i].releaseDateMovie
                    v.setOnClickListener {
                        startActivity<DetailMovieActivity>("ID" to data[i].idMovie)
                    }
                }
            }
        })
    }

    private fun setItemCast() {

    }

}
