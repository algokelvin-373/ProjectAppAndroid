package com.algokelvin.register.data.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.fragment.PageOneFragment;
import com.algokelvin.register.data.fragment.PageThreeFragment;
import com.algokelvin.register.data.fragment.PageTwoFragment;
import com.algokelvin.register.data.utils.TabPageLayer;

public class MainActivity extends TabPageLayer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] title = {"1", "2", "3"};
        Fragment[] fragments = {new PageOneFragment(), new PageTwoFragment(), new PageThreeFragment()};
        setTabPageLayer(R.id.tabHome, R.id.viewPager, title, fragments);

    }

}