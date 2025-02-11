package com.algokelvin.recordcallwa

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "RecordWaCall"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (!isNotificationServiceEnabled()) {
            Log.i(TAG, "Requesting Notification Access")
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        if (Settings.canDrawOverlays(this)) {
            Log.i(TAG, "Settings.canDrawOverlays")
            startService(Intent(this, FloatingService::class.java))
        } else {
            Log.i(TAG, "ACTION_MANAGE_OVERLAY_PERMISSION")
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val contentResolver = applicationContext.contentResolver
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(packageName) == true
    }
}