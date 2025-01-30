package com.algokelvin.recordcall.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat

class CallRecorderService : Service() {
    private val TAG = "RecordCallingLogger"
    private var mediaRecorder: MediaRecorder? = null
    private var filename: String = ""

    // Channel ID untuk notifikasi
    private val CHANNEL_ID = "CallRecorderChannel"
    private val NOTIFICATION_ID = 123

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
        super.onCreate()
        createNotificationChannel()

        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Call Recorder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sedang Merekam Panggilan")
            .setContentText("Tap untuk membuka aplikasi")
            .setSmallIcon(android.R.drawable.ic_menu_call)
            .setOngoing(true)
            .build()
    }

    private fun startRecording() {
        Log.i(TAG, "startRecording")
        try {
            // Mulai foreground service
            startForeground(NOTIFICATION_ID, buildNotification())

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