package com.algokelvin.recordcallwa

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class WhatsAppCallListener : NotificationListenerService() {
    private val TAG = "RecordWaCall"
    private lateinit var notificationText: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        notificationText = sbn.notification.extras.getString("android.text", "")

        Log.d(TAG, "Notification received: $packageName - Text: $notificationText")

        if (packageName == "com.whatsapp") {
            Log.d(TAG, "WhatsApp Notification: $notificationText")
        }

        // For Android 13
        if (packageName == "com.whatsapp" && notificationText != null) {
            if (notificationText.contains("Panggilan telepon berlangsung", true)) {
                Log.d(TAG, "WhatsApp Call Detected! Starting FloatingService...")
                val intent = Intent(applicationContext, FloatingService::class.java)
                applicationContext.startForegroundService(intent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // For Android 13
        if (!notificationText.contains("Panggilan telepon berlangsung", true)) {
            Log.d(TAG, "WhatsApp Call Ended! Stopping FloatingService...")
            stopService(Intent(this, FloatingService::class.java))
        }
    }
}
