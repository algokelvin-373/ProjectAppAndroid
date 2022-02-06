package com.algokelvin.moviecatalog.util

import android.app.Activity
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import java.util.*

object ConstMethodUI {
    fun Activity.glideImg(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }
    fun Fragment.glideImg(url: String, imageView: ImageView) {
        Glide.with(this).load(url).into(imageView)
    }
    fun TabLayout.titleTab(title: Array<Int>) {
        for (header in title.indices) {
            this.addTab(this.newTab().setText(title[header]))
        }
    }
    fun TabLayout.tabSelected(getTabText: (String) -> Unit) {
        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                getTabText(tab?.text.toString().toLowerCase(Locale.getDefault()))
            }
        })
    }
}