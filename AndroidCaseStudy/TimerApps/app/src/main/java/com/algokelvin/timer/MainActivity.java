package com.algokelvin.timer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvTimer, tvTimerJava, statusTimeOut, statusTimeOutJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        tvTimerJava = findViewById(R.id.tv_timer_java);
        statusTimeOut = findViewById(R.id.status_time_out);
        statusTimeOutJava = findViewById(R.id.status_time_out_java);

        // Call Method using Kotlin
        runOnUiThread(() -> {
            TimerKotlin.createTimer(tvTimer, statusTimeOut, 50, 100); // 5 seconds
        });

        // Call Method using Java
        runOnUiThread(() -> {
            TimerJava.createTimer(tvTimerJava, statusTimeOutJava, 100, 100); // 10 seconds
        });
    }
}