package com.algokelvin.recordcall.service

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log

class CallRecorderService : Service() {
    private val TAG = "RecordCallingLogger"
    private var mediaRecorder: MediaRecorder? = null
    private var filename: String = ""

    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_OFFHOOK -> startRecording()
                TelephonyManager.CALL_STATE_IDLE -> stopRecording()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private fun startRecording() {
        Log.i(TAG, "startRecording")
        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.VOICE_CALL)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                filename = "${Environment.getExternalStorageDirectory()}/${System.currentTimeMillis()}_recording.3gp"
                setOutputFile(filename)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting recording: ${e.message}")
        }
    }

    private fun stopRecording() {
        Log.i(TAG, "stopRecording")
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    override fun onDestroy() {
        super.onDestroy()
        (getSystemService(TELEPHONY_SERVICE) as TelephonyManager).listen(
            phoneStateListener,
            PhoneStateListener.LISTEN_NONE
        )
    }
}