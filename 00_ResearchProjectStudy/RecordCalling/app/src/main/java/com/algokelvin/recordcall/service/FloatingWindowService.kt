package com.algokelvin.recordcall.service

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
        createFloatingWindow()
    }

    private fun createFloatingWindow() {
        Log.i(TAG, "FloatingWindowService - createFloatingWindow")
        Toast.makeText(this, "Floating is run", Toast.LENGTH_SHORT).show()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_window, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 100
            y = 500
        }

        windowManager.addView(floatingView, params)

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
                    R.drawable.ic_record
            )
            Toast.makeText(this, 
                if (isRecording) "Recording started" else "Recording stopped", 
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized && ::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }
}