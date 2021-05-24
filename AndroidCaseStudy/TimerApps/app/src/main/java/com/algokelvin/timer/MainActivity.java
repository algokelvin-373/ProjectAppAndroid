package com.algokelvin.timer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvTimer, statusTimeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        statusTimeOut = findViewById(R.id.status_time_out);

        runOnUiThread(() -> {
            TimerJava.createTimer(tvTimer, statusTimeOut, 50, 100); // 5 seconds
        });
    }
}