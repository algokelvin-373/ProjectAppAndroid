package com.algokelvin.tablayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutFunction {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public TabLayoutFunction(TabLayout tabLayout, ViewPager viewPager) {
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
    }

    public void setTabLayoutTitle(String... titleTab) {
        for (String s : titleTab)
            tabLayout.addTab(tabLayout.newTab().setText(s));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void setTabLayoutAction(FragmentManager fm) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(fm, tabLayout.getTabCount());
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
