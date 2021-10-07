package com.algokelvin.register.data.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabPageLayer extends AppCompatActivity implements OnViewPager {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public void setTabPageLayer(int idTabLayout, int idViewPager, String[] titles, Fragment[] fragments) {
        setUI(idTabLayout, idViewPager);
        setTabPageFragment(titles, fragments);
    }

    private void setUI(int idTabLayout, int idViewPager) {
        tabLayout = findViewById(idTabLayout);
        viewPager = findViewById(idViewPager);
    }

    private void setTabPageFragment(String[] titles, Fragment[] fragments) {
        TabLayoutFunction tabLayoutFunction = new TabLayoutFunction(getSupportFragmentManager(), tabLayout, viewPager);
        tabLayoutFunction.setTitleTabLayout(titles);
        tabLayoutFunction.setViewPagerTabLayout(fragments);
        viewPager = tabLayoutFunction.getViewPager();
    }

    @Override
    public void onSetPage(int page) {
        viewPager.setCurrentItem(page);
    }
}
