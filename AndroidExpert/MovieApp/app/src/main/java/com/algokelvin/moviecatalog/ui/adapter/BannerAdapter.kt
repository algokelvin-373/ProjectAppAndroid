package com.algokelvin.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class BannerAdapter(
    private val count: Int,
    private val layout: Int,
    private val viewBanner: (View, Int) -> Unit
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(layout, container, false)
        viewBanner(view, position)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, o: Any): Boolean = view == o

    override fun getCount(): Int = count

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}