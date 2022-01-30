package com.algokelvin.moviecatalog.ui.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.ui.adapter.movie.MovieAdapter
import com.algokelvin.moviecatalog.model.DataMovie
import com.algokelvin.moviecatalog.onclicklisterner.CatalogClickListener
import com.algokelvin.moviecatalog.repository.MovieRepository
import com.algokelvin.moviecatalog.ui.activity.detailmovie.DetailMovieActivity
import com.algokelvin.moviecatalog.util.statusGone
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.startActivity
import java.util.*

class MovieFragment : Fragment() {
    private val movieViewModelFactory by lazy {
        MovieViewModelFactory(movieRepository = MovieRepository(), compositeDisposable = CompositeDisposable())
    }
    private val movieViewModel by lazy {
        ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
    }

    private val catalogClickListener = object : CatalogClickListener {
        override fun itemCatalogClick(id: Int?) {
            requireContext().startActivity<DetailMovieActivity>("ID" to id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieNowPlaying()
        setMovie(getString(R.string.now_playing).toLowerCase(Locale.getDefault()))
        tabMovieCatalogOnClick(tab_layout_movie)
    }

    private fun tabMovieCatalogOnClick(tabLayout: TabLayout) {
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_now_playing))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_popular))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_top_related))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_upcoming))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setMovie(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }
        })
    }

    private fun setMovieNowPlaying() {
        movieViewModel.rqsMovieNowPlaying()
        movieViewModel.rspMovieNowPlaying.observe(this, Observer {
            progress_content_movie_now_playing.visibility = statusGone
            setRecyclerViewMovie(rv_movie_now_playing, 2, it)
        })
    }

    private fun setMovie(type: String) {
        movieViewModel.rqsMovie(type)
        movieViewModel.rspMovie.observe(this, Observer {
            progress_content_movie.visibility = statusGone
            setRecyclerViewMovie(rv_movie, 1, it)
        })
    }

    private fun setRecyclerViewMovie(recyclerMovie: RecyclerView, type: Int, list: List<DataMovie>) {
        when(type) {
            1 -> recyclerMovie.layoutManager = LinearLayoutManager(requireContext())
            2 -> recyclerMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        recyclerMovie.setHasFixedSize(true)
        recyclerMovie.adapter = MovieAdapter(list, requireActivity(), type, catalogClickListener)
        recyclerMovie.adapter?.notifyDataSetChanged()
    }
}
