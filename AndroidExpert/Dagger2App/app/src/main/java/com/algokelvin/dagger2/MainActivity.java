package com.algokelvin.dagger2;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(context, "You Success Implement Dagger2", Toast.LENGTH_LONG).show();
    }
}