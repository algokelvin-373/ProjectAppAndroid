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
import com.algokelvin.moviecatalog.util.ConstMethodUI.glideImg
import com.algokelvin.moviecatalog.util.ConstMethodUI.tabSelected
import com.algokelvin.moviecatalog.util.ConstMethodUI.titleTab
import com.algokelvin.moviecatalog.util.ConstantVal
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_movie_banner.view.*
import kotlinx.android.synthetic.main.item_movie_catalog.view.*
import java.util.*

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    private val movieViewModelFactory by lazy {
        MovieViewModelFactory(MovieRepository(), CompositeDisposable())
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
        binding.tabLayoutMovie.apply { titleTab(ConstantVal.tabLayoutMovie) }
        tabLayout.tabSelected { tab -> setMovie(tab) }
    }

    private fun setMovieNowPlaying() {
        movieViewModel.rqsMovieNowPlaying()
        movieViewModel.rspMovieNowPlaying.observe(this, Observer {
            binding.apply {
                setRecyclerViewMovie(
                    progressContentMovieNowPlaying,
                    rvMovieNowPlaying,
                    R.layout.item_movie_banner,
                    it
                )
            }
        })
    }

    private fun setMovie(type: String) {
        movieViewModel.rqsMovie(type)
        movieViewModel.rspMovie.observe(this, Observer {
            binding.apply {
                setRecyclerViewMovie(
                    progressContentMovie,
                    rvMovie,
                    R.layout.item_movie_catalog,
                    it
                )
            }
        })
    }

    private fun setRecyclerViewMovie(
        progressBar: ProgressBar,
        recyclerView: RecyclerView,
        type: Int,
        list: List<DataMovie>
    ) {
        progressBar.visibility = ConstantVal.statusGone
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = DataAdapter(list.size, type) { v, i -> setItemView(type, v, list[i]) }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setItemView(type: Int, view: View, data: DataMovie) {
        when (type) {
            R.layout.item_movie_catalog -> {
                glideImg("${BuildConfig.URL_IMAGE}${data.poster}", view.image_catalog)
                view.title_movie_catalog.text = data.title
                view.date_movie_catalog.text = data.release
            }
            R.layout.item_movie_banner -> {
                glideImg("${BuildConfig.URL_IMAGE}${data.poster}", view.poster_movie_now_playing)
                view.title_movie_now_playing.text = data.title
            }
        }
        view.setOnClickListener {
            val intentDetail = Intent(requireContext(), DetailMovieActivity::class.java)
            intentDetail.putExtra("ID", data.id)
            startActivity(intentDetail)
        }
    }

}
