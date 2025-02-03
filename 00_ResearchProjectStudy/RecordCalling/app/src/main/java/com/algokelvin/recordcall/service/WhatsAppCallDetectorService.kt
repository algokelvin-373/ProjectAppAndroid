package com.algokelvin.recordcall.service

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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

    private val requestReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "ACTION_REQUEST_CALL_STATE") {
                val currentState = detectCallState()
                sendCallStateBroadcast(currentState)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Daftarkan receiver untuk request state
        val filter = IntentFilter("ACTION_REQUEST_CALL_STATE")
        LocalBroadcastManager.getInstance(this).registerReceiver(requestReceiver, filter)
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
            try {
                // Gunakan kombinasi 3 indikator utama
                val indicators = listOf(
                    "com.whatsapp:id/end_call_button",
                    "com.whatsapp:id/call_duration",
                    "com.whatsapp:id/voip_call_time"
                )

                // Cek semua node yang terlihat
                val isInCall = indicators.any { id ->
                    root.findAccessibilityNodeInfosByViewId(id).any { node ->
                        node.isVisibleToUser && node.isClickable
                    }
                }

                // Logging detail
                Log.d("CallDetection",
                    """
                WhatsApp Call Detection:
                - End Button: ${root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/end_call_button").size}
                - Duration: ${root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/call_duration").size}
                - VOIP Time: ${root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/voip_call_time").size}
                Result: $isInCall
                """.trimIndent()
                )

                isInCall
            } finally {
                root.recycle()
            }
        } ?: false
    }

    private fun sendCallStateBroadcast(isInCall: Boolean) {
        Log.d(TAG, "Call state changed: $isInCall")
        val intent = Intent("WHATSAPP_CALL_STATE_CHANGED").apply {
            putExtra("isInCall", isInCall)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        // Start/stop floating window
        val serviceIntent = Intent(this@WhatsAppCallDetectorService, FloatingWindowService::class.java)
        if (isInCall) {
            startService(serviceIntent)
        } else {
            stopService(serviceIntent)
        }
    }

    override fun onInterrupt() {
        sendCallStateBroadcast(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(requestReceiver)
        handler.removeCallbacksAndMessages(null)
    }
}