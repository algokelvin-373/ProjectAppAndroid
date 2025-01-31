package com.algokelvin.recordcall.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class WhatsAppCallDetectorService: AccessibilityService() {
    private val TAG = "RecordCallingLogger"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(TAG, "Run onAccessibilityEvent")
        event?.let {
            if (it.packageName == "com.whatsapp") {
                val isCallActive = detectCallState(it)
                sendCallStateBroadcast(isCallActive)
            }
        }
    }

    private fun detectCallState(event: AccessibilityEvent): Boolean {
        Log.i(TAG, "Run detectCallState")
        return event.source?.let { root ->
            // Cari indikator panggilan WhatsApp
            val callNodes = root.findAccessibilityNodeInfosByViewId("com.whatsapp:id/")
            val isInCall = callNodes.any { node ->
                node.text?.contains("ongoing call", ignoreCase = true) == true ||
                        node.contentDescription?.contains("call", ignoreCase = true) == true
            }
            root.recycle()
            isInCall
        } ?: false
    }

    private fun sendCallStateBroadcast(isInCall: Boolean) {
        Log.i(TAG, "Run sendCallStateBroadcast")
        val intent = Intent("WHATSAPP_CALL_STATE_CHANGED").apply {
            putExtra("isInCall", isInCall)
        }
        sendBroadcast(intent)
    }

    override fun onInterrupt() { }
}