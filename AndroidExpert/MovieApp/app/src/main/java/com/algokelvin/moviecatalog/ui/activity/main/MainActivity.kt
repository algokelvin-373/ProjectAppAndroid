package com.algokelvin.moviecatalog.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.ui.adapter.MainPageAdapter
import com.algokelvin.moviecatalog.ui.fragment.movie.MovieFragment
import com.algokelvin.moviecatalog.ui.fragment.tvshow.TVShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val movie = MovieFragment()
    private val tvShow = TVShowFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPageAdapter = MainPageAdapter(supportFragmentManager)
        mainPageAdapter.addPageMenu(movie, tvShow)
        main_viewpager.adapter = mainPageAdapter
        tab_layout_main.setupWithViewPager(main_viewpager)

        tab_layout_main.getTabAt(0)?.setText(R.string.movie)
        tab_layout_main.getTabAt(1)?.setText(R.string.tvshow)
    }
}
