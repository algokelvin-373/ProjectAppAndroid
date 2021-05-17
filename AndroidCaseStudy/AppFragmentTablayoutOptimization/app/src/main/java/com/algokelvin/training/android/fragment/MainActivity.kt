package com.algokelvin.training.android.fragment

import algokelvin.android.tablayout.AppsViewPageAdapter
import algokelvin.android.tablayout.SetupViewPager.setTextNameTabs
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

        tabsLayout.setTextNameTabs("Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5")
    }
}