package com.algokelvin.moviecatalog.ui.activity.detail

import android.app.Activity
import android.content.Intent
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.ActivityDetailTvshowBinding
import com.algokelvin.moviecatalog.model.entity.DataCast
import com.algokelvin.moviecatalog.model.entity.DataTVShow
import com.algokelvin.moviecatalog.model.entity.DetailTVShow
import com.algokelvin.moviecatalog.ui.activity.detail.movie.DetailMovieActivity
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethodUI.glideImg
import kotlinx.android.synthetic.main.item_cast.view.*
import kotlinx.android.synthetic.main.item_catalog_other.view.*

class InitViewDetailTV(
    private val context: Activity,
    private val binding: ActivityDetailTvshowBinding
) {
    fun setDetail(data: DetailTVShow) {
        binding.apply {
            context.glideImg("${BuildConfig.URL_POSTER}${data.background}", imagePosterCatalogTvShow)
            context.glideImg("${BuildConfig.URL_IMAGE}${data.poster}", imageTvShowCatalog)
            titleCatalogTvShow.text = data.title
            dateReleaseCatalogTvShow.text = data.firstDateTVShow
            seasonsCatalogTvShow.text = data.seasonsTVShow
            episodesReleaseCatalogTvShow.text = data.episodesTVShow
            voteAverageReleaseCatalogTvShow.text = data.voteAverageTVShow.toString()
            voteCountReleaseCatalogTvShow.text = data.voteCountTVShow.toString()
            overviewCatalogTvShow.text = data.descriptionTVShow
        }
    }
    fun setDataCast(data: List<DataCast>) {
        val ctx = context
        binding.rvCastTvShow.apply {
            setHasFixedSize(true)
            adapter = DataAdapter(data.size, R.layout.item_cast) { v, i ->
                val urlImage = "${BuildConfig.URL_IMAGE}${data[i].posterCast}"
                ctx.glideImg(urlImage, v.image_cast)
                v.name_cast.text = data[i].nameCast
                v.character_cast.text = data[i].characterCast
            }
        }
    }
    fun setSimilar(data: List<DataTVShow>) {
        val ctx = context
        binding.rvSimilarTvShow.apply {
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
    fun setRecommendation(data: List<DataTVShow>) {
        val ctx = context
        binding.rvRecommendationTvShow.apply {
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