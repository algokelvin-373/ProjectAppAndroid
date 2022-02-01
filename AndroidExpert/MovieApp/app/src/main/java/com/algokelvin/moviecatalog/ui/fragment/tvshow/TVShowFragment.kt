package com.algokelvin.moviecatalog.ui.fragment.tvshow

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
import com.algokelvin.moviecatalog.ui.activity.detailtv.DetailTVShowActivity
import com.algokelvin.moviecatalog.ui.adapter.DataAdapter
import com.algokelvin.moviecatalog.ui.adapter.tvshow.BannerTVShowAdapter
import com.algokelvin.moviecatalog.util.statusGone
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_tvshow.*
import kotlinx.android.synthetic.main.item_tvshow_catalog.view.*
import org.jetbrains.anko.startActivity
import java.util.*

class TVShowFragment : Fragment() {
    private lateinit var binding: FragmentTvshowBinding

    private val tvShowViewModelFactory by lazy {
        TVShowViewModelFactory(tvShowRepository = TVShowRepository(), compositeDisposable = CompositeDisposable())
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
        binding.apply {
            tabLayoutTvShow.addTab(tabLayoutTvShow.newTab().setText(R.string.tvShow_airing_today))
            tabLayoutTvShow.addTab(tabLayoutTvShow.newTab().setText(R.string.tvShow_on_the_air))
            tabLayoutTvShow.addTab(tabLayoutTvShow.newTab().setText(R.string.tvShow_popular))
            tabLayoutTvShow.addTab(tabLayoutTvShow.newTab().setText(R.string.tvShow_top_related))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setTVShow(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }
        })
    }

    private fun setTVShowAiringToday() {
        tvShowViewModel.rqsTVShowAiringToday()
        tvShowViewModel.rspTVShowAiringToday.observe(this, Observer {
            binding.apply {
                viewpagerTvshowBanner.adapter = BannerTVShowAdapter(requireContext(), it)
                wormDotsIndicatorTvshow.setViewPager(viewpagerTvshowBanner)
            }
        })
    }

    private fun setTVShow(type: String) {
        tvShowViewModel.rqsTVShow(type)
        tvShowViewModel.rspTVShow.observe(this, Observer { data ->
            binding.apply {
                progressContentTvShow.visibility = statusGone
                rvTvShow.setHasFixedSize(true)
                rvTvShow.adapter = DataAdapter(data.size, R.layout.item_tvshow_catalog) { v, i ->
                    setItemTvShow(v, data[i])
                }
                rvTvShow.adapter?.notifyDataSetChanged()
            }
        })
    }

    private fun setItemTvShow(view: View, data: DataTVShow) {
        val imageURL = "${BuildConfig.URL_POSTER}${data.backgroundTVShow}"
        Glide.with(this@TVShowFragment).load(imageURL).into(view.image_tvshow_catalog)
        view.title_tvshow_catalog.text = data.titleTVShow
        view.date_tvshow_catalog.text = data.firstDateTVShow
        view.setOnClickListener {
            requireContext().startActivity<DetailTVShowActivity>("ID" to data.idTVShow)
        }
    }

}