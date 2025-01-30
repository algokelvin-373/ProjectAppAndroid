package com.algokelvin.recordcall.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
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
            // Pindahkan startForeground ke sebelum memulai MediaRecorder
            //startForeground(NOTIFICATION_ID, buildNotification())

            mediaRecorder = MediaRecorder().apply {
                // Gunakan MIC sebagai ganti VOICE_CALL
                setAudioSource(MediaRecorder.AudioSource.MIC)

                // Format output
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)

                // Encoder
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

                filename = "${getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/${System.currentTimeMillis()}_recording.3gp"
                setOutputFile(filename)

                // Optimasi untuk panggilan
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(192000)

                prepare()
                start()

                // Aktifkan speakerphone
                (getSystemService(AUDIO_SERVICE) as AudioManager).apply {
                    isSpeakerphoneOn = true
                    mode = AudioManager.MODE_IN_COMMUNICATION
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting recording: ${e.message}")
            //stopForeground(true)
            stopSelf()
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