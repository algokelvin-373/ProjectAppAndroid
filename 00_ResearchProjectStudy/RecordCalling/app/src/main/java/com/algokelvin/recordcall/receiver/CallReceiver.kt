package com.algokelvin.recordcall.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
    private val TAG = "RecordCallingLogger"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.PHONE_STATE") {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            when (state) {
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    // Panggilan aktif
                    Log.i(TAG, "Panggilan Aktif")
                }
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    // Panggilan berakhir
                    Log.i(TAG, "Panggilan Berakhir")
                }
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    // Panggilan masuk
                    Log.i(TAG, "Panggilan Masuk")
                }
            }
        }
    }
}