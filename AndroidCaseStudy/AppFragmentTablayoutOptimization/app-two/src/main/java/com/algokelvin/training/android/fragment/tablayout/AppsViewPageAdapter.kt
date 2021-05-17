package com.algokelvin.training.android.fragment.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AppsViewPageAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val pageAppsCatalog: MutableList<Fragment> = mutableListOf()

    override fun getItem(position: Int): Fragment = pageAppsCatalog[position]
    override fun getCount(): Int = pageAppsCatalog.size

    fun addAppsPage(vararg fragments: Fragment) {
        for (fragment in fragments)
            this.pageAppsCatalog.add(fragment)
    }
}