package com.algokelvin.tablayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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
        TabLayoutFunction tabLayoutFunction = new TabLayoutFunction(tabLayout, viewPager);
        tabLayoutFunction.setTabLayoutTitle(titleTab);
        tabLayoutFunction.setTabLayoutAction(getSupportFragmentManager());

    }
}