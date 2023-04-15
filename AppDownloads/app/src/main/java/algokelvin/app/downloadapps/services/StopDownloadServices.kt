package com.tsm.aptoide.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class StopDownloadServices : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.stopService(Intent(context, DownloadServices::class.java))

        with(NotificationManagerCompat.from(context)) {
            //cancel(intent.getIntExtra("version_code", 0))
            cancel(intent.getLongExtra("version_code", 0).toInt())
        }
    }
}