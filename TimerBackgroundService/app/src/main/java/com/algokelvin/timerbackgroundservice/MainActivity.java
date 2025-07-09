package com.algokelvin.timerbackgroundservice;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends Activity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "TimerServices";

    private TextView tvTimer;
    private Button btnGo;

    private BroadcastReceiver timerUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String time = intent.getStringExtra("time");
            if (time != null && tvTimer != null) {
                tvTimer.setText(time);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        btnGo = findViewById(R.id.btn_go);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerService();
            }
        });

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(timerUpdateReceiver, new IntentFilter("TIMER_UPDATE"));

        Log.d(TAG, "MainActivity onCreate");
    }

    private void startTimerService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Intent serviceIntent = new Intent(MainActivity.this, TimerService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(timerUpdateReceiver);
    }
}