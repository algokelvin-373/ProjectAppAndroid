package com.algokelvin.timerbackgroundservice.workmanager;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastHelper {
    public static void sendUpdateBroadcast(Context context, String time) {
        Intent intent = new Intent("TIMER_UPDATE");
        intent.putExtra("time", time);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}