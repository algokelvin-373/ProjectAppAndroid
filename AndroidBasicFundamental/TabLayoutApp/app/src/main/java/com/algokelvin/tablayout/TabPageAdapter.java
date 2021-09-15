package com.algokelvin.tablayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class TabPageAdapter extends FragmentStatePagerAdapter {
    int totalTabs;

    public TabPageAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new OneFragment();
            case 1: return new TwoFragment();
            case 2: return new ThreeFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
