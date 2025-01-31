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

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val whatsappPackages = listOf(
                "com.whatsapp",         // Versi regular
                "com.whatsapp.w4a",     // Versi Business
                "com.whatsapp.plus"     // Mods
            )
            if (whatsappPackages.contains(it.packageName?.toString())) {
                val isCallActive = detectCallState(it)
                sendCallStateBroadcast(isCallActive)
            }
        }
    }

    private fun detectCallState(event: AccessibilityEvent): Boolean {
        return event.source?.let { root ->
            // Daftar ID view terbaru untuk panggilan WhatsApp (versi 2.23.10.76)
            val callIndicators = listOf(
                "com.whatsapp:id/call_duration",      // Durasi panggilan
                "com.whatsapp:id/call_toolbar",       // Toolbar panggilan
                "com.whatsapp:id/voip_call_button",   // Tombol panggilan
                "com.whatsapp:id/end_call_button"     // Tombol akhir panggilan
            )

            val isInCall = callIndicators.any { id ->
                root.findAccessibilityNodeInfosByViewId(id).isNotEmpty()
            }
            root.recycle()
            Log.d(TAG, "Detected call state: $isInCall")
            isInCall
        } ?: false
    }

    private fun sendCallStateBroadcast(isInCall: Boolean) {
        Log.i(TAG, "sendCallStateBroadcast")
        if (isInCall != lastCallState) {
            Log.i(TAG, "isInCall != lastCallState")
            handler.postDelayed({
                val intent = Intent("WHATSAPP_CALL_STATE_CHANGED").apply {
                    putExtra("isInCall", isInCall)
                }
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                lastCallState = isInCall
            }, 500) // Delay 500ms untuk pastikan UI stabil
        }
    }

    override fun onInterrupt() { }
}