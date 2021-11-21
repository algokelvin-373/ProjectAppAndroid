package com.algokelvin.tablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    private final Fragment[] fragments;
    private final int totalTabs;

    public TabPageAdapter(FragmentManager fm, Fragment[] fragment, int totalTabs) {
        super(fm);
        this.fragments = fragment;
        this.totalTabs = totalTabs;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
