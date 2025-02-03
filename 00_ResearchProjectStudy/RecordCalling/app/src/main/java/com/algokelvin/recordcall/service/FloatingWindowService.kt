package com.algokelvin.recordcall.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.algokelvin.recordcall.R

class FloatingWindowService : Service() {
    private val TAG = "RecordCallingLogger"
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var isRecording = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "FloatingWindowService - onCreate")
        startForeground(1, createNotification())
        createFloatingWindow()
    }

    private fun createNotification(): Notification {
        val channelId = "floating_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Floating Window",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Call Recorder Active")
            .setSmallIcon(R.drawable.ic_notification)
            .build()
    }

    private fun createFloatingWindow() {
        try {
            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            floatingView = LayoutInflater.from(this).inflate(R.layout.floating_window, null)

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.CENTER
                x = 0
                y = 0
            }

            windowManager.addView(floatingView, params)
            Log.d(TAG, "Window successfully added")

            val btnClose = floatingView.findViewById<ImageView>(R.id.btn_close)
            val btnRecord = floatingView.findViewById<ImageView>(R.id.btn_record)

            btnClose.setOnClickListener {
                stopSelf()
            }

            btnRecord.setOnClickListener {
                isRecording = !isRecording
                btnRecord.setImageResource(
                    if (isRecording)
                        R.drawable.ic_stop
                    else
                        R.drawable.ic_recording
                )
                Toast.makeText(this,
                    if (isRecording) "Recording started" else "Recording stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating window: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized && ::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }
}