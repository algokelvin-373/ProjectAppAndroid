package com.algovin373.project.moviecatalog.adapter.tvshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.algovin373.project.moviecatalog.BuildConfig
import com.algovin373.project.moviecatalog.R
import com.algovin373.project.moviecatalog.model.DataTVShow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_tvshow_banner.view.*

class BannerTVShowAdapter(private val context: Context, private val tvShowBanners: List<DataTVShow>) : PagerAdapter() {

    override fun isViewFromObject(view: View, o: Any): Boolean = view == o

    override fun getCount(): Int = tvShowBanners.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tvshow_banner, container, false)

        val imageURL = "${BuildConfig.URL_POSTER}${tvShowBanners[position].backgroundTVShow}"
        Glide.with(context).load(imageURL).into(view.image_tvshow_banner)
        view.title_tvshow_banner.text = tvShowBanners[position].titleTVShow

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}