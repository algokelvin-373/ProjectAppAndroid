package com.algokelvin.recordcallwa

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
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

        if (!Settings.canDrawOverlays(this)) {
            Log.i(TAG, "Requesting Overlay Permission")
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
                return
            }
        }

    }

    private fun isNotificationServiceEnabled(): Boolean {
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(packageName) == true
    }
}