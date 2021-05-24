package com.algokelvin.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer timer = null;
    private TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createTimer(600, 100); // 1 minute
            }
        });
    }
    private void createTimer(int delay, int maxLoop) {
        timer = new CountDownTimer((long) delay * maxLoop, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = millisUntilFinished / 1000 % 60;
                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // TO DO
            }
        };
        timer.start();
    }
}