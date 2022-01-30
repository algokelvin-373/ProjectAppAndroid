package com.algovin373.project.moviecatalog.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.adapter.tvshow.BannerTVShowAdapter
import com.algovin373.project.moviecatalog.adapter.tvshow.TVShowAdapter
import com.algovin373.project.moviecatalog.onclicklisterner.CatalogClickListener
import com.algovin373.project.moviecatalog.repository.TVShowRepository
import com.algovin373.project.moviecatalog.ui.activity.DetailTVShowActivity
import com.algovin373.project.moviecatalog.util.statusGone
import com.algovin373.project.moviecatalog.viewmodel.TVShowViewModel
import com.algovin373.project.moviecatalog.viewmodelfactory.TVShowViewModelFactory
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.jetbrains.anko.startActivity
import java.util.*

class TVShowFragment : Fragment() {
    private val tvShowViewModel by lazy {
        ViewModelProviders.of(this,
            TVShowViewModelFactory(tvShowRepository = TVShowRepository(), compositeDisposable = CompositeDisposable()))
            .get(TVShowViewModel::class.java)
    }

    private val catalogClickListener = object : CatalogClickListener {
        override fun itemCatalogClick(id: Int?) {
            requireContext().startActivity<DetailTVShowActivity>("ID" to id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowViewModel.getDataTVShowAiringToday().observe(this, Observer {
            viewpager_tvshow_banner.adapter = BannerTVShowAdapter(requireContext(), it)
            worm_dots_indicator_tvshow.setViewPager(viewpager_tvshow_banner)
        })

        setTVShow(getString(R.string.tvShow_airing_today).toLowerCase(Locale.getDefault()))
        tab_layout_tvShow.addTab(tab_layout_tvShow.newTab().setText(R.string.tvShow_airing_today))
        tab_layout_tvShow.addTab(tab_layout_tvShow.newTab().setText(R.string.tvShow_on_the_air))
        tab_layout_tvShow.addTab(tab_layout_tvShow.newTab().setText(R.string.tvShow_popular))
        tab_layout_tvShow.addTab(tab_layout_tvShow.newTab().setText(R.string.tvShow_top_related))
        tabTVShowCatalogOnClick(tab_layout_tvShow)
    }

    private fun tabTVShowCatalogOnClick(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setTVShow(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }
        })
    }

    private fun setTVShow(type: String) {
        tvShowViewModel.getDataTVShow(type).observe(this, Observer {
            progress_content_tvShow.visibility = statusGone
            rv_tvShow.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = TVShowAdapter(
                    it,
                    requireActivity(),
                    catalogClickListener
                )
                adapter?.notifyDataSetChanged()
            }
        })
    }
}