package com.algokelvin.moviecatalog.ui.fragment.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.FragmentMovieBinding
import com.algokelvin.moviecatalog.model.DataMovie
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.ui.activity.detail.movie.DetailMovieActivity
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethod.glideImg
import com.algokelvin.moviecatalog.util.statusGone
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_movie_banner.view.*
import kotlinx.android.synthetic.main.item_movie_catalog.view.*
import java.util.*

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    private val movieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository = MovieRepository(), compositeDisposable = CompositeDisposable())
    }
    private val movieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieNowPlaying()
        setMovie(getString(R.string.now_playing).toLowerCase(Locale.getDefault()))
        tabMovieCatalogOnClick(binding.tabLayoutMovie)
    }

    private fun tabMovieCatalogOnClick(tabLayout: TabLayout) {
        binding.apply {
            tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText(R.string.movie_now_playing))
            tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText(R.string.movie_popular))
            tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText(R.string.movie_top_related))
            tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText(R.string.movie_upcoming))
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab?) {}
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    setMovie(tab?.text.toString().toLowerCase(Locale.getDefault()))
                }
            })
        }
    }

    private fun setMovieNowPlaying() {
        movieViewModel.rqsMovieNowPlaying()
        movieViewModel.rspMovieNowPlaying.observe(this, Observer {
            binding.apply {
                setRecyclerViewMovie(progressContentMovieNowPlaying, rvMovieNowPlaying, R.layout.item_movie_banner, it)
            }
        })
    }

    private fun setMovie(type: String) {
        movieViewModel.rqsMovie(type)
        movieViewModel.rspMovie.observe(this, Observer {
            binding.apply {
                setRecyclerViewMovie(progressContentMovie, rvMovie, R.layout.item_movie_catalog, it)
            }
        })
    }

    private fun setRecyclerViewMovie(
        progressBar: ProgressBar,
        recyclerView: RecyclerView,
        type: Int,
        list: List<DataMovie>
    ) {
        progressBar.visibility = statusGone
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = DataAdapter(list.size, type) { v, i -> setItemView(type, v, list[i]) }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setItemView(type: Int, view: View, data: DataMovie) {
        when (type) {
            R.layout.item_movie_catalog -> {
                glideImg("${BuildConfig.URL_IMAGE}${data.posterMovie}", view.image_movie_catalog)
                view.title_movie_catalog.text = data.titleMovie
                view.date_movie_catalog.text = data.releaseDateMovie
            }
            R.layout.item_movie_banner -> {
                glideImg("${BuildConfig.URL_IMAGE}${data.posterMovie}", view.poster_movie_now_playing)
                view.title_movie_now_playing.text = data.titleMovie
            }
        }
        view.setOnClickListener {
            val intentDetail = Intent(requireContext(), DetailMovieActivity::class.java)
            intentDetail.putExtra("ID", data.idMovie)
            startActivity(intentDetail)
        }
    }

}
