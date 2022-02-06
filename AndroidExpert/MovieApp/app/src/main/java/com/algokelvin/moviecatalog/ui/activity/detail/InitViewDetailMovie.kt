package com.algokelvin.moviecatalog.ui.activity.detail

import android.app.Activity
import android.content.Intent
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.ActivityDetailMovieBinding
import com.algokelvin.moviecatalog.model.entity.DataCast
import com.algokelvin.moviecatalog.model.entity.DataMovie
import com.algokelvin.moviecatalog.model.entity.DetailMovie
import com.algokelvin.moviecatalog.model.entity.Keyword
import com.algokelvin.moviecatalog.ui.activity.detail.movie.DetailMovieActivity
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethodUI.glideImg
import kotlinx.android.synthetic.main.item_cast.view.*
import kotlinx.android.synthetic.main.item_catalog_other.view.*

class InitViewDetailMovie(
    private val context: Activity,
    private val binding: ActivityDetailMovieBinding
) {
    fun setDetail(data: DetailMovie) {
        binding.apply {
            context.glideImg("${BuildConfig.URL_POSTER}${data.background}", imagePosterCatalog)
            context.glideImg("${BuildConfig.URL_IMAGE}${data.poster}", imageCatalog)
            titleCatalog.text = data.title
            dateReleaseCatalog.text = data.release
            statusReleaseCatalog.text = data.statusMovie
            runtimeReleaseCatalog.text = data.runtimeMovie.toString()
            voteAverageReleaseCatalog.text = data.voteAverageMovie.toString()
            voteCountReleaseCatalog.text = data.voteCountMovie.toString()
            overviewCatalog.text = data.overviewMovie
        }
    }
    fun setKeyword(data: ArrayList<Keyword>) {
        var keyword = ""
        for (i in 0 until data.size) {
            keyword += if (i == data.size-1) "${data[i].keyword}" else "${data[i].keyword}, "
        }
        binding.keywordCatalog.text = keyword
    }
    fun setDataCast(data: List<DataCast>) {
        val ctx = context
        binding.rvCast.apply {
            setHasFixedSize(true)
            adapter = DataAdapter(data.size, R.layout.item_cast) { v, i ->
                val urlImage = "${BuildConfig.URL_IMAGE}${data[i].posterCast}"
                ctx.glideImg(urlImage, v.image_cast)
                v.name_cast.text = data[i].nameCast
                v.character_cast.text = data[i].characterCast
            }
        }
    }
    fun setSimilar(data: List<DataMovie>) {
        val ctx = context
        binding.rvSimilar.apply {
            setHasFixedSize(true)
            adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                val urlImage = "${BuildConfig.URL_POSTER}${data[i].background}"
                ctx.glideImg(urlImage, v.image_other_movie)
                v.title.text = data[i].title
                v.date_release.text = data[i].release
                v.setOnClickListener {
                    val intentDetail = Intent(ctx, DetailMovieActivity::class.java)
                    intentDetail.putExtra("ID", data[i].id)
                    ctx.startActivity(intentDetail)
                }
            }
        }
    }
    fun setRecommendation(data: List<DataMovie>) {
        val ctx = context
        binding.rvSimilar.apply {
            setHasFixedSize(true)
            adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                val urlImage = "${BuildConfig.URL_POSTER}${data[i].background}"
                ctx.glideImg(urlImage, v.image_other_movie)
                v.title.text = data[i].title
                v.date_release.text = data[i].release
                v.setOnClickListener {
                    val intentDetail = Intent(ctx, DetailMovieActivity::class.java)
                    intentDetail.putExtra("ID", data[i].id)
                    ctx.startActivity(intentDetail)
                }
            }
        }
    }
}