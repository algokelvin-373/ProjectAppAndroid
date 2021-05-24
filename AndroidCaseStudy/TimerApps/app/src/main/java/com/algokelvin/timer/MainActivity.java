package com.algokelvin.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer timer = null;
    private TextView tvTimer, statusTimeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        statusTimeOut = findViewById(R.id.status_time_out);

        runOnUiThread(() -> {
            createTimer(50, 100); // 5 seconds
        });
    }
    private void createTimer(int delay, int maxLoop) {
        timer = new CountDownTimer((long) delay * maxLoop, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = millisUntilFinished / 1000 % 60;
                Log.i("timer-long", minutes+" "+seconds);
                tvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                statusTimeOut.setVisibility(View.VISIBLE);
                tvTimer.setText("00:00");
            }
        };
        timer.start();
    }
}