package com.algokelvin.timerbackgroundservice;

import static com.algokelvin.timerbackgroundservice.Utils.formatTime;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {

    private static final String TAG = "TimerService";
    private static final String CHANNEL_ID = "timer_service_channel";
    private static final int NOTIFICATION_ID = 1;

    private long startTimeMillis = 0L;
    private boolean isRunning = false;

    private Handler handler;
    private Runnable runnable;
    private NotificationManager notificationManager;

    public TimerService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        //createNotificationChannel();

        Log.d(TAG, "isRunning: "+ !isRunning);
        if (!isRunning) {
            startTimeMillis = System.currentTimeMillis();
            isRunning = true;
            runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Runnable running...");
                    updateNotification();
                    handler.postDelayed(this, 500); // Update setiap 0.5 detik
                }
            };
            handler.post(runnable);
        }

        // Start foreground
        Notification notification = getNotification("00:00:00");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, notification);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }

        return START_STICKY;
    }

    private void updateNotification() {
        long elapsed = System.currentTimeMillis() - startTimeMillis;
        String time = formatTime(elapsed);
        Log.d(TAG, "Updating notification...");
        Log.d(TAG, "Elapsed: " + elapsed + " ms -> Time: " + time);

        Notification notification = getNotification(time);
        notificationManager.notify(NOTIFICATION_ID, notification);

        Intent broadcastIntent = new Intent("TIMER_UPDATE");
        broadcastIntent.putExtra("time", time);
        broadcastIntent.putExtra("startTimeMillis", startTimeMillis);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private Notification getNotification(String timeText) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer Berjalan")
                .setContentText("Waktu: " + timeText)
                .setSmallIcon(R.drawable.ic_run_history)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Foreground",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}