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

        Fragment[] fragments = {new PageOneFragment(), new PageTwoFragment(), new PageThreeFragment()};
        setTabPageLayer(9, R.id.tabHome, R.id.viewPager, fragments);
        setPageDetailData(DataRegisterActivity.class);
        setPageEnds(fragments.length);

    }
}