package com.algokelvin.register.data.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutFunction {
    private final FragmentManager fragmentManager;
    private final TabLayout tabLayout;
    private final ViewPager viewPager;

    public TabLayoutFunction(FragmentManager fragmentActivity, TabLayout tabLayout, ViewPager viewPager) {
        this.fragmentManager = fragmentActivity;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setTitleTabLayout(String ...titleTabLayout) {
        for (String s : titleTabLayout)
            tabLayout.addTab(tabLayout.newTab().setText(s));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void setViewPagerTabLayout(int countTabs, Fragment ...fragments) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(fragmentManager, countTabs, fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
