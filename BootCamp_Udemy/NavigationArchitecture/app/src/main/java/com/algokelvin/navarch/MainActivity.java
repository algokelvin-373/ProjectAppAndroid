package com.algokelvin.navarch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.algokelvin.navarch.ui.main.MainFragment;
import com.algokelvin.navarch.ui.main.SecondFragment;

public class MainActivity extends AppCompatActivity implements SecondFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}