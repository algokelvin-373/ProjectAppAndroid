package com.algokelvin.timer;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TimerJava {
    public static void createTimer(
            TextView tvTimer,
            TextView statusTimeOut,
            int delay,
            int maxLoop
    ) {
        CountDownTimer timer = new CountDownTimer((long) delay * maxLoop, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = millisUntilFinished / 1000 % 60;
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
