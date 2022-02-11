package com.algokelvin.moviecatalog.util

import android.view.View
import com.algokelvin.moviecatalog.R

object ConstantVal {
    const val statusGone = View.GONE

    val tabLayoutMovie = arrayOf(
        R.string.movie_now_playing,
        R.string.movie_popular,
        R.string.movie_top_related,
        R.string.movie_upcoming
    )

    val tabLayoutTv = arrayOf(
        R.string.tvShow_airing_today,
        R.string.tvShow_on_the_air,
        R.string.tvShow_popular,
        R.string.tvShow_top_related
    )

}