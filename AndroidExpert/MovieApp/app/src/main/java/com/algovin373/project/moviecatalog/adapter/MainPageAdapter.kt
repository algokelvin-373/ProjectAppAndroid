package com.algovin373.project.moviecatalog.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPageAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val pageMovieCatalog: MutableList<Fragment> = mutableListOf()

    override fun getItem(page: Int): Fragment = pageMovieCatalog[page]

    override fun getCount(): Int = pageMovieCatalog.size

    fun addPageMenu(vararg fragments: Fragment) {
        for (fragment in fragments)
            this.pageMovieCatalog.add(fragment)
    }
}