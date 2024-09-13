package com.algokelvin.register.data.utils;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabPageLayer extends AppCompatActivity implements OnViewPager, OnDataPass {
    private int pages = 1, count = 0, pageEnds;
    private Class classDetailPage;
    private String[] dataObj;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public void setTabPageLayer(int countData, int idTabLayout, int idViewPager, Fragment[] fragments) {
        dataObj = new String[countData];
        setUI(idTabLayout, idViewPager);
        setTabPageFragment(fragments);
    }

    public void setPageDetailData(Class classDetailPage) {
        this.classDetailPage = classDetailPage;
    }

    public void setPageEnds(int pageEnds) {
        this.pageEnds = pageEnds;
    }

    private void setUI(int idTabLayout, int idViewPager) {
        tabLayout = findViewById(idTabLayout);
        viewPager = findViewById(idViewPager);
    }

    private void setTabPageFragment(Fragment[] fragments) {
        TabLayoutFunction tabLayoutFunction = new TabLayoutFunction(getSupportFragmentManager(), tabLayout, viewPager);
        tabLayoutFunction.setViewPagerTabLayout(fragments.length, fragments);
        viewPager = tabLayoutFunction.getViewPager();
    }

    @Override
    public void onSetPage(int page) {
        viewPager.setCurrentItem(page);
    }

    @Override
    public void btnSendData(String... data) {
        for (String datum : data) {
            dataObj[count++] = datum;
        }
        if (pages == pageEnds) {
            Intent intentDetails = new Intent(this, classDetailPage);
            intentDetails.putExtra("message", dataObj);
            startActivity(intentDetails);
            finish();
        } else
            pages++;
    }

    @Override
    public void minusCountData(int count) {
        this.count -= count;
        pages--;
    }
}
