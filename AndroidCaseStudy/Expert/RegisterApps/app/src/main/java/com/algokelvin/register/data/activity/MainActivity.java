package com.algokelvin.register.data.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.fragment.PageOneFragment;
import com.algokelvin.register.data.fragment.PageThreeFragment;
import com.algokelvin.register.data.fragment.PageTwoFragment;
import com.algokelvin.register.data.model.OnDataPass;
import com.algokelvin.register.data.utils.TabPageLayer;

public class MainActivity extends TabPageLayer implements OnDataPass {
    private int pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String data = "This is my data from activity";

        PageOneFragment pageOneFragment = new PageOneFragment();
        Fragment[] fragments = {pageOneFragment, new PageTwoFragment(), new PageThreeFragment()};
        setTabPageLayer(R.id.tabHome, R.id.viewPager, fragments);

        Bundle b = new Bundle();
        b.putString("message", data);
        pageOneFragment.setArguments(b);

    }

    @Override
    public void btnSendData(String data) {
        Log.i("message_factivity " + pages, data);
        if (pages == 3) {
            Intent intentDetails = new Intent(this, DataRegisterActivity.class);
            intentDetails.putExtra("message", data);
            startActivity(intentDetails);
            finish();
        } else
            pages++;
    }
}