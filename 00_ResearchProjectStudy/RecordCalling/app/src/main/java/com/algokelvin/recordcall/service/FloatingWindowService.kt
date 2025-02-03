package com.algokelvin.recordcall.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.media.AudioManager
import android.media.MediaRecorder
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import com.algokelvin.recordcall.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FloatingWindowService : Service() {
    private val TAG = "RecordCallingLogger"
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var isRecording = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "FloatingWindowService - onCreate")
        startForeground(1, createNotification())
        createFloatingWindow()
    }

    private fun createNotification(): Notification {
        val channelId = "floating_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Floating Window",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Call Recorder Active")
            .setSmallIcon(R.drawable.ic_notification)
            .build()
    }

    private fun createFloatingWindow() {
        try {
            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            floatingView = LayoutInflater.from(this).inflate(R.layout.floating_window, null)

            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.CENTER
                x = 0
                y = -200
            }

            windowManager.addView(floatingView, params)
            Log.d(TAG, "Window successfully added")

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
                        R.drawable.ic_recording
                )
                if (isRecording) {
                    startRecordingWhatsAppCall()
                } else {
                    stopRecordingWhatsAppCall()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating window: ${e.message}")
        }
    }

    private fun startRecordingWhatsAppCall() {
        try {
            Log.i(TAG, "startRecording - WA Call")

            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.mode = AudioManager.MODE_NORMAL
            audioManager.isSpeakerphoneOn = false
            Thread.sleep(300) // Jeda untuk stabilisasi audio
            Log.i(TAG, "startRecording - Step 1")

            audioManager.apply {
                mode = AudioManager.MODE_IN_COMMUNICATION
                isSpeakerphoneOn = true
                setParameters("noise_suppression=auto")
                setStreamVolume(
                    AudioManager.STREAM_VOICE_CALL,
                    (getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL) * 0.75).toInt(),
                    0
                )
            }
            Log.i(TAG, "startRecording - Step 2")

            val audioSources = arrayOf(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                MediaRecorder.AudioSource.MIC,
                MediaRecorder.AudioSource.VOICE_RECOGNITION
            )
            Log.i(TAG, "startRecording - Step 3")

            val outputDir = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "WhatsApp Records").apply { mkdirs() }
            } else {
                filesDir
            }
            Log.i(TAG, "startRecording - Step 4")

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val filename = "REC_WA_${timestamp}.mp4"
            val filePath = File(outputDir, filename).absolutePath
            Log.i(TAG, "startRecording - Step 5")

            mediaRecorder = MediaRecorder().apply {
                Log.i(TAG, "startRecording - Step 6")
                for (source in audioSources) {
                    try {
                        setAudioSource(source)
                        break
                    } catch (e: Exception) {
                        Log.w(TAG, "Audio source $source unavailable: ${e.message}")
                    }
                }
                Log.i(TAG, "startRecording - Step 7")

                // Konfigurasi format dan encoder
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                // Parameter low-latency
                setAudioSamplingRate(8000)
                setAudioEncodingBitRate(12200)
                setAudioChannels(1)
                setOutputFile(filePath)
                Log.i(TAG, "startRecording - Step 8")

                try {
                    prepare()
                } catch (e: Exception) {
                    Log.e(TAG, "Prepare error: ${e.stackTraceToString()}")
                    // Fallback ke format MP4 jika 3GP gagal
                    try {
                        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                        setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                        prepare()
                    } catch (e2: Exception) {
                        Log.e(TAG, "MP4 prepare failed: ${e2.stackTraceToString()}")
                        throw e2
                    }
                }
                Log.i(TAG, "startRecording - Step 9")

                start()

                Log.i(TAG, "startRecording - Step 10")
            }

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()

            Log.i(TAG, "startRecording - Step 11")

            // 7. Paksa scan media
            MediaScannerConnection.scanFile(
                this,
                arrayOf(filePath),
                arrayOf("audio/mp4")
            ) { path, uri ->
                Log.d(TAG, "Scanned $path: $uri")
            }

            Log.i(TAG, "startRecording - Step 12")

            audioManager.setParameters("voice_communication=on")
        } catch (e: Exception) {
            Log.e(TAG, "Error start recording WA Call: ${e.message} - ${e.localizedMessage}")
            stopRecordingWhatsAppCall()
        }
    }

    private fun stopRecordingWhatsAppCall() {
        Log.i(TAG, "stopRecording - WA Call")
        try {
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.apply {
                mode = AudioManager.MODE_NORMAL
                isSpeakerphoneOn = false
            }

            mediaRecorder?.stop()
            mediaRecorder?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error stop recording WA Call: ${e.message}")
        }
        mediaRecorder = null
        //stopForeground(true)
        //stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized && ::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }
}