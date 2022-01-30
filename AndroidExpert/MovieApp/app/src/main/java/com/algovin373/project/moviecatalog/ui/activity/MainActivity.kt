package com.algovin373.project.moviecatalog.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.adapter.MainPageAdapter
import com.algovin373.project.moviecatalog.ui.fragment.MovieFragment
import com.algovin373.project.moviecatalog.ui.fragment.TVShowFragment
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
