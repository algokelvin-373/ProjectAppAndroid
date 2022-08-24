package com.algokelvin.training.android.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appsViewPageAdapter = AppsViewPageAdapter(supportFragmentManager)
        appsViewPageAdapter.addAppsPage(TabsFragment(0), TabsFragment(1), TabsFragment(2), TabsFragment(3), TabsFragment(4))
        layoutViewPager.adapter = appsViewPageAdapter
        tabsLayout.setupWithViewPager(layoutViewPager)

        tabsLayout.getTabAt(0)?.text = "Tab 1"
        tabsLayout.getTabAt(1)?.text = "Tab 2"
        tabsLayout.getTabAt(2)?.text = "Tab 3"
        tabsLayout.getTabAt(3)?.text = "Tab 4"
        tabsLayout.getTabAt(4)?.text = "Tab 5"
    }
}