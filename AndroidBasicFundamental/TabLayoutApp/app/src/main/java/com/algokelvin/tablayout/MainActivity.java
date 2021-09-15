package com.algokelvin.tablayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        String[] titleTab = {"Tab 1", "Tab 2", "Tab 3"};
        Fragment[] fragment = {new OneFragment(), new TwoFragment(), new ThreeFragment()};
        TabLayoutFunction tabLayoutFunction = new TabLayoutFunction(fragment, tabLayout, viewPager);
        tabLayoutFunction.setTabLayoutTitle(titleTab);
        tabLayoutFunction.setTabLayoutAction(getSupportFragmentManager());

    }
}