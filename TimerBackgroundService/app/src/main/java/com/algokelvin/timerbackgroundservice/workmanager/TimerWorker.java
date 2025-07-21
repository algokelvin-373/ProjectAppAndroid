package com.algokelvin.timerbackgroundservice.workmanager;

import static com.algokelvin.timerbackgroundservice.Utils.formatTime;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TimerWorker extends Worker {

    private static final String TAG = "TimerWorker";

    public TimerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork called");

        long startTimeMillis = System.currentTimeMillis();

        // Simulasi timer selama 1 jam
        for (int i = 0; i < 3600; i++) {
            try {
                Thread.sleep(1000); // Delay 1 detik
                long elapsed = System.currentTimeMillis() - startTimeMillis;

                // Update notifikasi & kirim broadcast
                NotificationHelper.showNotification(getApplicationContext(), formatTime(elapsed));
                BroadcastHelper.sendUpdateBroadcast(getApplicationContext(), formatTime(elapsed));

            } catch (InterruptedException e) {
                return Result.failure();
            }
        }

        return Result.success();
    }
}