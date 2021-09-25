package com.algokelvin.lifecycle;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import static com.algokelvin.lifecycle.TableLayoutFunction.setTabLayout;

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
//        tablayout.addTab(tablayout.newTab().setText("Three"));
//        tablayout.addTab(tablayout.newTab().setText("Four"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        count = tablayout.getTabCount();

        Fragment[] fragments = {
                new OneFragment(),
                new TwoFragment()
//                new ThreeFragment(),
//                new FourFragment()
        };

        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), count, fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        setTabLayout(tablayout, viewPager);

        TextView txtAction = findViewById(R.id.txt_action);
        txtAction.setOnClickListener(v -> {
            Toast.makeText(this, "You click data " + data, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onDataPass(String data) {
        this.data = data;
        Toast.makeText(this, "You click data " + data, Toast.LENGTH_SHORT).show();
    }
}