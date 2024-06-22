package com.algokelvin.lifestyle.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.algokelvin.lifestyle.R;
import com.algokelvin.lifestyle.fragment.PageOneFragment;
import com.algokelvin.lifestyle.fragment.PageThreeFragment;
import com.algokelvin.lifestyle.fragment.PageTwoFragment;
import com.algokelvin.lifestyle.utils.OnViewPager;
import com.algokelvin.lifestyle.utils.TabLayoutFunction;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements OnViewPager {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tablayout = findViewById(R.id.tabHome);
        viewPager = findViewById(R.id.viewPager);
        String[] title = {"1", "2", "3"};
        Fragment[] fragments = {new PageOneFragment(), new PageTwoFragment(), new PageThreeFragment()};

        TabLayoutFunction tabLayoutFunction = new TabLayoutFunction(getSupportFragmentManager(), tablayout, viewPager);
        tabLayoutFunction.setTitleTabLayout(title);
        tabLayoutFunction.setViewPagerTabLayout(fragments);
        viewPager = tabLayoutFunction.getViewPager();

    }

    @Override
    public void onSetPage(int page) {
        viewPager.setCurrentItem(page);
    }
}