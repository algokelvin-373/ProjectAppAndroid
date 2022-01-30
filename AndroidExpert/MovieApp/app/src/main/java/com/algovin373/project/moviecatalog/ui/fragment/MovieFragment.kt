package com.algovin373.project.moviecatalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.adapter.movie.MovieAdapter
import com.algovin373.project.moviecatalog.model.DataMovie
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.repository.MovieRepository
import com.algovin373.project.moviecatalog.ui.activity.DetailMovieActivity
import com.algovin373.project.moviecatalog.util.statusGone
import com.algovin373.project.moviecatalog.viewmodel.MovieViewModel
import com.algovin373.project.moviecatalog.viewmodelfactory.MovieViewModelFactory
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.startActivity
import java.util.*

class MovieFragment : Fragment() {
    private val movieViewModel by lazy {
        ViewModelProviders.of(this,
            MovieViewModelFactory(movieRepository = MovieRepository(), compositeDisposable = CompositeDisposable()))
            .get(MovieViewModel::class.java)
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

        movieViewModel.getDataMovieNowPlaying().observe(this, Observer {
            progress_content_movie_now_playing.visibility = statusGone
            setRecyclerViewMovie(rv_movie_now_playing, 2, it)
        })

        setMovie(getString(R.string.now_playing).toLowerCase(Locale.getDefault()))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_now_playing))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_popular))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_top_related))
        tab_layout_movie.addTab(tab_layout_movie.newTab().setText(R.string.movie_upcoming))
        tabMovieCatalogOnClick(tab_layout_movie)
    }

    private fun tabMovieCatalogOnClick(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setMovie(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }
        })
    }

    private fun setMovie(type: String) {
        movieViewModel.getDataMovie(type).observe(this, Observer {
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
        recyclerMovie.adapter = MovieAdapter(
            list,
            requireActivity(),
            type,
            catalogClickListener
        )
        recyclerMovie.adapter?.notifyDataSetChanged()
    }
}
