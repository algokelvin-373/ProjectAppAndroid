package com.algokelvin.moviecatalog.ui.activity.detail.tv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.algokelvin.moviecatalog.databinding.ActivityDetailTvshowBinding
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.ui.activity.detail.InitViewDetailTV
import io.reactivex.disposables.CompositeDisposable

class DetailTVShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvshowBinding
    private lateinit var initViewDetailTV: InitViewDetailTV

    private val detailTVShowViewModelFactory by lazy {
        DetailTVShowViewModelFactory(TVShowRepository(), CompositeDisposable())
    }

    private val detailTVShowViewModel by lazy {
        ViewModelProviders.of(this, detailTVShowViewModelFactory)
            .get(DetailTVShowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvshowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewDetailTV = InitViewDetailTV(this, binding)
        val id = intent.getIntExtra("ID", 0)

        setDetail(id)
        setCast(id)
        setSimilar(id)
        setRecommended(id)

    }

    private fun setDetail(id: Int) {
        detailTVShowViewModel.rqsDetailTVShow(id).observe(this, Observer {
            initViewDetailTV.setDetail(it)
        })
    }

    private fun setCast(id: Int) {
        detailTVShowViewModel.rqsCastTVShow(id)
        detailTVShowViewModel.rspCastTVShow.observe(this, Observer {
            initViewDetailTV.setDataCast(it)
        })
    }

    private fun setSimilar(id: Int) {
        detailTVShowViewModel.rqsSimilarTVShow(id)
        detailTVShowViewModel.rspSimilarTVShow.observe(this, Observer { data ->
            initViewDetailTV.setSimilar(data)
        })
    }

    private fun setRecommended(id: Int) {
        detailTVShowViewModel.rqsRecommendationTVShow(id)
        detailTVShowViewModel.rspRecommendationTVShow.observe(this, Observer { data ->
            initViewDetailTV.setRecommendation(data)
        })
    }

}
