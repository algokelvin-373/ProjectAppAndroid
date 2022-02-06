package com.algokelvin.moviecatalog.ui.fragment.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.algokelvin.moviecatalog.BuildConfig
import com.algokelvin.moviecatalog.R
import com.algokelvin.moviecatalog.databinding.FragmentTvshowBinding
import com.algokelvin.moviecatalog.model.DataTVShow
import com.algokelvin.moviecatalog.repository.TVShowRepository
import com.algokelvin.moviecatalog.ui.activity.detail.tv.DetailTVShowActivity
import com.algokelvin.moviecatalog.ui.adapter.BannerAdapter
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.util.ConstMethodUI.glideImg
import com.algokelvin.moviecatalog.util.ConstMethodUI.tabSelected
import com.algokelvin.moviecatalog.util.ConstMethodUI.titleTab
import com.algokelvin.moviecatalog.util.ConstantVal
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_tvshow_banner.view.*
import kotlinx.android.synthetic.main.item_tvshow_catalog.view.*
import java.util.*

class TVShowFragment : Fragment() {
    private lateinit var binding: FragmentTvshowBinding

    private val tvShowViewModelFactory by lazy {
        TVShowViewModelFactory(TVShowRepository(), CompositeDisposable())
    }

    private val tvShowViewModel by lazy {
        ViewModelProviders.of(this, tvShowViewModelFactory).get(TVShowViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTVShowAiringToday()
        setTVShow(getString(R.string.tvShow_airing_today).toLowerCase(Locale.getDefault()))
        tabTVShowCatalogOnClick(binding.tabLayoutTvShow)
    }

    private fun tabTVShowCatalogOnClick(tabLayout: TabLayout) {
        binding.tabLayoutTvShow.apply { titleTab(ConstantVal.tabLayoutTv) }
        tabLayout.tabSelected { tab -> setTVShow(tab) }
    }

    private fun setTVShowAiringToday() {
        tvShowViewModel.rqsTVShowAiringToday()
        tvShowViewModel.rspTVShowAiringToday.observe(this, Observer { data ->
            binding.viewpagerTvshowBanner.apply {
                adapter = BannerAdapter(data.size, R.layout.item_tvshow_banner) { v, i ->
                    val imageURL = "${BuildConfig.URL_POSTER}${data[i].background}"
                    glideImg(imageURL, v.image_tvshow_banner)
                    v.title_tvshow_banner.text = data[i].title
                }
            }
            binding.wormDotsIndicatorTvshow.setViewPager(binding.viewpagerTvshowBanner)
        })
    }

    private fun setTVShow(type: String) {
        tvShowViewModel.rqsTVShow(type)
        tvShowViewModel.rspTVShow.observe(this, Observer { data ->
            binding.apply {
                progressContentTvShow.visibility = ConstantVal.statusGone
                rvTvShow.setHasFixedSize(true)
                rvTvShow.adapter = DataAdapter(data.size, R.layout.item_tvshow_catalog) { v, i ->
                    setItemTvShow(v, data[i])
                }
                rvTvShow.adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun setItemTvShow(view: View, data: DataTVShow) {
        val imageURL = "${BuildConfig.URL_POSTER}${data.background}"
        glideImg(imageURL, view.image_tvshow_catalog)
        view.title_tvshow_catalog.text = data.title
        view.date_tvshow_catalog.text = data.firstDateTVShow
        view.setOnClickListener {
            val intentDetail = Intent(requireContext(), DetailTVShowActivity::class.java)
            intentDetail.putExtra("ID", data.id)
            startActivity(intentDetail)
        }
    }

}