package com.algokelvin.recordcall.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaRecorder
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        try {
            // Pindahkan startForeground ke sebelum memulai MediaRecorder
            startForeground(NOTIFICATION_ID, buildNotification())

            // 1. Atur mode audio SEBELUM memulai recorder
            audioManager.apply {
                mode = AudioManager.MODE_IN_COMMUNICATION  // <-- INI YANG DITAMBAHKAN
                isSpeakerphoneOn = true
                setParameters("noise_suppression=auto")  // Optimasi kualitas audio
            }

            // 2. Atur volume maksimal untuk input/output
            audioManager.setStreamVolume(
                AudioManager.STREAM_VOICE_CALL,
                (audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL) * 0.75).toInt(),
                0
            )

            // Check directory is exist
            val outputDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: return
            if (!outputDir.exists()) outputDir.mkdirs()

            // Create name file
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val filename = "REC_${timestamp}.mp4"
            val filePath = File(outputDir, filename).absolutePath
            Log.i(TAG, "create file path: "+ filePath)

            // 4. Configuration MediaRecorder
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(filePath)
                setAudioSamplingRate(16000)  // Lower rate untuk voice call
                setAudioEncodingBitRate(64000)

                prepare()
                start()
            }

            // 5. Optimasi tambahan setelah recording mulai
            audioManager.setParameters("voice_communication=on")
        } catch (e: Exception) {
            Log.e(TAG, "Error starting recording: ${e.message}\n${e.stackTraceToString()}")

            // Cek permission
            if (e is SecurityException) {
                Log.e(TAG, "Security Exception - Check permissions")
            }

            // Reset audio mode jika gagal
            audioManager.mode = AudioManager.MODE_NORMAL
            audioManager.isSpeakerphoneOn = false

            // Hentikan service
            stopForeground(true)
            stopSelf()
        }
    }

    private fun stopRecording() {
        try {
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.apply {
                mode = AudioManager.MODE_NORMAL
                isSpeakerphoneOn = false
            }

            mediaRecorder?.stop()
            mediaRecorder?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Stop error: ${e.message}")
        }
        mediaRecorder = null
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        (getSystemService(TELEPHONY_SERVICE) as TelephonyManager).listen(
            phoneStateListener,
            PhoneStateListener.LISTEN_NONE
        )
    }
}