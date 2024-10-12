package com.algokelvin.register.data.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private final Fragment[] fragments;
    int totalTabs;

    public TabPageAdapter(FragmentManager fm, int totalTabs, Fragment[] fragments) {
        super(fm);
        this.totalTabs = totalTabs;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
