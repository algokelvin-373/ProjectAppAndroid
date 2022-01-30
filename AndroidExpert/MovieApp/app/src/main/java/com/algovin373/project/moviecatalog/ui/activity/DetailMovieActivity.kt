package com.algovin373.project.moviecatalog.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.adapter.CastAdapter
import com.algovin373.project.moviecatalog.adapter.movie.OtherAdapterMovie
import com.algovin373.project.moviecatalog.model.DataCast
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.viewmodel.DetailMovieViewModel
import com.algovin373.project.moviecatalog.viewmodelfactory.DetailMovieViewModelFactory
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.startActivity

class DetailMovieActivity : AppCompatActivity() {
    private val detailMovieViewModel by lazy {
        ViewModelProviders.of(this,
            DetailMovieViewModelFactory(movieRepository = MovieRepository(), compositeDisposable = CompositeDisposable()))
            .get(DetailMovieViewModel::class.java)
    }

    private val catalogClickListener = object : CatalogClickListener {
        override fun itemCatalogClick(id: Int?) {
            startActivity<DetailMovieActivity>("ID" to id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val id = intent.getIntExtra("ID", 0)

        detailMovieViewModel.setDetailMovie(id).observe(this, Observer {
            Glide.with(this).load("${BuildConfig.URL_POSTER}${it.backgroundMovie}").into(image_poster_catalog_movie)
            Glide.with(this).load("${BuildConfig.URL_IMAGE}${it.posterMovie}").into(image_movie_catalog)
            title_catalog_movie.text = it.titleMovie
            date_release_catalog_movie.text = it.releaseDateMovie
            status_release_catalog_movie.text = it.statusMovie
            runtime_release_catalog_movie.text = it.runtimeMovie.toString()
            vote_average_release_catalog_movie.text = it.voteAverageMovie.toString()
            vote_count_release_catalog_movie.text = it.voteCountMovie.toString()
            overview_catalog_movie.text = it.overviewMovie
        })

        detailMovieViewModel.setKeywordMovie(id).observe(this, Observer {
            var keyword = ""
            for (i in 0 until it.size) {
                keyword += if (i == it.size-1) "${it[i].keyword}" else "${it[i].keyword}, "
            }
            keyword_catalog_movie.text = keyword
        })

        detailMovieViewModel.setCastMovie(id).observe(this, Observer {
            setMovieRecyclerView(rv_cast_movie, 1, it, emptyList())
        })

        detailMovieViewModel.setSimilarMovie(id).observe(this, Observer {
            setMovieRecyclerView(rv_similar_movie, 2, emptyList(), it)
        })

        detailMovieViewModel.setRecommendationMovie(id).observe(this, Observer {
            setMovieRecyclerView(rv_recommendation_movie, 2, emptyList(), it)
        })

        btn_back_to_menu.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setMovieRecyclerView(rv: RecyclerView, type: Int, listCast: List<DataCast>, listMovie: List<DataMovie>) {
        rv.apply {
            setHasFixedSize(true)
            when(type) {
                1 -> {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = CastAdapter(listCast, context)
                }
                2 -> {
                    layoutManager = LinearLayoutManager(context)
                    adapter = OtherAdapterMovie(
                        listMovie,
                        context,
                        catalogClickListener
                    )
                }
            }
            adapter?.notifyDataSetChanged()
        }
    }
}
