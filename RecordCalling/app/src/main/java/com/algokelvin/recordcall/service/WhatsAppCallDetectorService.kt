package com.algokelvin.recordcall.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class WhatsAppCallDetectorService: AccessibilityService() {
    private val TAG = "RecordCallingLogger"
    private var lastCallState = false
    private val handler = Handler(Looper.getMainLooper())
    private var cooldown = false

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (cooldown) return

        event?.let {
            when (event.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    handleWindowStateChange(event)
                }
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                    handleContentChange(event)
                }
                else -> {}
            }
        }
    }

    private fun handleWindowStateChange(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: return
        if (packageName.startsWith("com.whatsapp")) {
            checkCallStateWithDelay()
        }
    }

    private fun handleContentChange(event: AccessibilityEvent) {
        if (event.source?.packageName?.toString()?.startsWith("com.whatsapp") == true) {
            checkCallStateWithDelay()
        }
    }

    private fun checkCallStateWithDelay() {
        if (cooldown) return
        cooldown = true

        handler.postDelayed({
            val isInCall = detectCallState()
            if (isInCall != lastCallState) {
                sendCallStateBroadcast(isInCall)
                lastCallState = isInCall
            }
            cooldown = false
        }, 1000) // Cooldown 1 detik
    }

    private fun detectCallState(): Boolean {
        return rootInActiveWindow?.let { root ->
            // Deteksi yang lebih akurat dengan 2 kondisi wajib
            //val hasCallDuration = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/call_duration").isNotEmpty()
//            val hasCallToolbar = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/call_toolbar").isNotEmpty()
//            val hasVoipCallButton = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/voip_call_button").isNotEmpty()
//            val hasEndCallButton = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/end_call_button").isNotEmpty()

            // Hanya return true jika kedua kondisi terpenuhi
            //Log.i(TAG, "hasCallDuration: $hasCallDuration")
//            Log.i(TAG, "hasCallToolbar: $hasCallToolbar")
//            Log.i(TAG, "hasVoipCallButton: $hasVoipCallButton")
//            Log.i(TAG, "hasEndCallButton: $hasEndCallButton")
            val result = true
            Log.i(TAG, "Result: $result")
            result
        } ?: false
    }

    private fun sendCallStateBroadcast(isInCall: Boolean) {
        Log.d(TAG, "Call state changed: $isInCall")
        val intent = Intent("WHATSAPP_CALL_STATE_CHANGED").apply {
            putExtra("isInCall", isInCall)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onInterrupt() { }

    override fun onServiceConnected() {
        super.onServiceConnected()
        sendPersistentCallState()
    }

    private fun sendPersistentCallState() {
        handler.post(object : Runnable {
            override fun run() {
                val isInCall = true // Force true untuk testing
                sendCallStateBroadcast(isInCall)
                handler.postDelayed(this, 3000) // Update setiap 3 detik
            }
        })
    }
}