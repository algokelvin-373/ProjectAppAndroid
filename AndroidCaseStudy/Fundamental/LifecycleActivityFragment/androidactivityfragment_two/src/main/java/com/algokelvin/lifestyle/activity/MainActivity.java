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
import com.algokelvin.lifestyle.utils.TabPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements OnViewPager {
    private int count = 0;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tablayout = findViewById(R.id.tabHome);
        viewPager = findViewById(R.id.viewPager);

        tablayout.addTab(tablayout.newTab().setText("One"));
        tablayout.addTab(tablayout.newTab().setText("Two"));
        tablayout.addTab(tablayout.newTab().setText("Three"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        count = tablayout.getTabCount();

        PageOneFragment oneFragment = new PageOneFragment();
        PageTwoFragment twoFragment = new PageTwoFragment();
        PageThreeFragment threeFragment = new PageThreeFragment();
        Fragment[] fragments = {oneFragment, twoFragment, threeFragment};

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), count, fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.scr

    }

    @Override
    public void onSetPage(int page) {
        viewPager.setCurrentItem(page);
    }
}