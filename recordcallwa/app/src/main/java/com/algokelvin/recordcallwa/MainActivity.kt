package com.algokelvin.recordcallwa

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val TAG = "RecordWaCall"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (Settings.canDrawOverlays(this)) {
            Log.i(TAG, "Settings.canDrawOverlays")
            startService(Intent(this, FloatingService::class.java))
        } else {
            Log.i(TAG, "ACTION_MANAGE_OVERLAY_PERMISSION")
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(intent)
        }
    }
}