package com.algokelvin.recordcallwa

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.content.Intent
import android.util.Log

class WhatsAppCallListener : NotificationListenerService() {
    private val TAG = "RecordWaCall"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val notificationText = sbn.notification.extras.getString("android.text", "")

        Log.d(TAG, "Notification from: $packageName - Text: $notificationText")

        if (packageName == "com.whatsapp") {
            Log.d(TAG, "WA Notification: $notificationText")
        }

        if (packageName == "com.whatsapp" && (notificationText?.contains("Berdering…", true) == true ||
                    notificationText?.contains("calling you", true) == true)) {
            Log.d(TAG, "WhatsApp Call Detected! Starting FloatingService...")
            startService(Intent(this, FloatingService::class.java))
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        /*if (sbn.packageName == "com.whatsapp") {
            Log.d(TAG, "WhatsApp Call Ended! Stopping FloatingService...")
            stopService(Intent(this, FloatingService::class.java))
        }*/
    }
}
