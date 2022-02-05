package com.algokelvin.moviecatalog.ui.activity.detail.tv

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.ActivityDetailTvshowBinding
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethod.glideImg
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail_tvshow.*
import kotlinx.android.synthetic.main.item_cast.view.*
import kotlinx.android.synthetic.main.item_catalog_other.view.*

class DetailTVShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvshowBinding

    private val detailTVShowViewModelFactory by lazy {
        DetailTVShowViewModelFactory(tvShowRepository = TVShowRepository(), compositeDisposable = CompositeDisposable())
    }

    private val detailTVShowViewModel by lazy {
        ViewModelProviders.of(this, detailTVShowViewModelFactory).get(DetailTVShowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvshowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("ID", 0)

        setDetail(id)
        setCast(id)
        setSimilar(id)
        setRecommended(id)

        btn_back_to_menu.setOnClickListener { finish() }
    }

    private fun setDetail(id: Int) {
        detailTVShowViewModel.rqsDetailTVShow(id).observe(this, Observer {
            binding.apply {
                glideImg("${BuildConfig.URL_POSTER}${it.backgroundTVShow}", imagePosterCatalogTvShow)
                glideImg("${BuildConfig.URL_IMAGE}${it.posterTVShow}", imageTvShowCatalog)
                titleCatalogTvShow.text = it.titleTVShow
                dateReleaseCatalogTvShow.text = it.firstDateTVShow
                seasonsCatalogTvShow.text = it.seasonsTVShow
                episodesReleaseCatalogTvShow.text = it.episodesTVShow
                voteAverageReleaseCatalogTvShow.text = it.voteAverageTVShow.toString()
                voteCountReleaseCatalogTvShow.text = it.voteCountTVShow.toString()
                overviewCatalogTvShow.text = it.descriptionTVShow
            }
        })
    }

    private fun setCast(id: Int) {
        detailTVShowViewModel.rqsCastTVShow(id)
        detailTVShowViewModel.rspCastTVShow.observe(this, Observer {
            binding.rvCastTvShow.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(it.size, R.layout.item_cast) { v, i ->
                    val urlImage = "${BuildConfig.URL_IMAGE}${it[i].posterCast}"
                    glideImg(urlImage, v.image_cast)
                    v.name_cast.text = it[i].nameCast
                    v.character_cast.text = it[i].characterCast
                }
            }
        })
    }

    private fun setSimilar(id: Int) {
        detailTVShowViewModel.rqsSimilarTVShow(id)
        detailTVShowViewModel.rspSimilarTVShow.observe(this, Observer { data ->
            binding.rvSimilarTvShow.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                    val urlImage = "${BuildConfig.URL_POSTER}${data[i].backgroundTVShow}"
                    Glide.with(context).load(urlImage).into(v.image_other_movie)
                    v.title.text = data[i].titleTVShow
                    v.date_release.text = data[i].firstDateTVShow
                    v.setOnClickListener {
                        val intentDetail = Intent(this@DetailTVShowActivity, DetailTVShowActivity::class.java)
                        intentDetail.putExtra("ID", data[i].idTVShow)
                        startActivity(intentDetail)
                    }
                }
            }
        })
    }

    private fun setRecommended(id: Int) {
        detailTVShowViewModel.rqsRecommendationTVShow(id)
        detailTVShowViewModel.rspRecommendationTVShow.observe(this, Observer { data ->
            binding.rvRecommendationTvShow.apply {
                setHasFixedSize(true)
                adapter = DataAdapter(data.size, R.layout.item_catalog_other) { v, i ->
                    val urlImage = "${BuildConfig.URL_POSTER}${data[i].backgroundTVShow}"
                    Glide.with(context).load(urlImage).into(v.image_other_movie)
                    v.title.text = data[i].titleTVShow
                    v.date_release.text = data[i].firstDateTVShow
                    v.setOnClickListener {
                        val intentDetail = Intent(this@DetailTVShowActivity, DetailTVShowActivity::class.java)
                        intentDetail.putExtra("ID", data[i].idTVShow)
                        startActivity(intentDetail)
                    }
                }
            }
        })
    }

}
