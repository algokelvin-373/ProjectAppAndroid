package com.androidtutz.anushka.activitystatesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getLifecycle().addObserver(new DemoAppComponent("SecondActivity"));

        Log.i(TAG,"***************    SecondActivity onCreate() ");
    }


}
