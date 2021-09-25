package com.algokelvin.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements OnDataPass {
    private int count = 0;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tablayout = findViewById(R.id.tabHome);
        ViewPager viewPager = findViewById(R.id.viewPager);

        tablayout.addTab(tablayout.newTab().setText("One"));
        tablayout.addTab(tablayout.newTab().setText("Two"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        count = tablayout.getTabCount();

        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        Fragment[] fragments = {oneFragment, twoFragment};

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), count, fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setCurrentItem(1);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0: oneFragment.passData();
                        break;
                    case 1: twoFragment.passData();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("tabUnselected", "" + tab.getPosition());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TextView txtAction = findViewById(R.id.txt_action);
        txtAction.setOnClickListener(v -> {
            Toast.makeText(this, "You click data " + data, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onDataPass(String data) {
        this.data = data;
    }
}