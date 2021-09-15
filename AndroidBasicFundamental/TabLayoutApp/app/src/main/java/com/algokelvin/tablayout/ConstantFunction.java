package com.algokelvin.tablayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ConstantFunction {
    public static void setTabLayout(TabLayout tabBni46, ViewPager viewPager) {
        tabBni46.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
