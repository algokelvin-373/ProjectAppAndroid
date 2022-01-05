package com.algokelvin.tablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutFunction {
    private final Fragment[] fragments;
    private final TabLayout tabLayout;
    private final ViewPager viewPager;

    public TabLayoutFunction(Fragment[] fragments, TabLayout tabLayout, ViewPager viewPager) {
        this.fragments = fragments;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
    }

    public void setTabLayoutTitle(String... titleTab) {
        for (String s : titleTab)
            tabLayout.addTab(tabLayout.newTab().setText(s));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void setTabLayoutAction(FragmentManager fm) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(fm, fragments, tabLayout.getTabCount());
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
