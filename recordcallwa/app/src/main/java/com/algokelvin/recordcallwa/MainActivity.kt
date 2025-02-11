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
    private val RECORD_AUDIO_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        checkAndRequestAudioPermission()

        if (!isNotificationServiceEnabled()) {
            Log.i(TAG, "Requesting Notification Access")
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        if (!Settings.canDrawOverlays(this)) {
            Log.i(TAG, "Requesting Overlay Permission")
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }

    }

    private fun checkAndRequestAudioPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // Munculkan dialog permintaan izin
                requestPermissions(arrayOf(android.Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_CODE)
                false
            }
        } else {
            true
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners?.contains(packageName) == true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Izin RECORD_AUDIO diberikan")
            } else {
                Log.e(TAG, "Izin RECORD_AUDIO ditolak")
            }
        }
    }

}