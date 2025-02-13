package com.algokelvin.recordcallwa

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.AudioManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.algokelvin.recordcallwa.recorder.ServerRecorderListener
import com.algokelvin.recordcallwa.recorder.ServerRecordingState

class FloatingService : Service() {
    private val TAG = "RecordWaCall"
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private lateinit var recorder: MediaRecorder
    private var isRecording = false

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "FloatingService started!")

        val channelId = "floating_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Floating Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Floating Service Active")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(1, notification)

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_layout, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 100
        params.y = 100

        windowManager.addView(floatingView, params)

        floatingView.findViewById<View>(R.id.ivCloseFloating).setOnClickListener {
            stopSelf()
        }

        val icRecorder = floatingView.findViewById<ImageView>(R.id.ivCallIcon)
        val txtRecorder = floatingView.findViewById<TextView>(R.id.tvFloatingText)
        icRecorder.setOnClickListener {
            icRecorder.setImageResource(R.drawable.ic_recorder_on)
            txtRecorder.text = "Via Call WA Berjalan\nRecord Memproses"
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                    if (audioManager.isMusicActive) {
                        Log.e(TAG, "Audio sedang digunakan oleh aplikasi lain!")
                    } else {
                        Log.d(TAG, "Recording Started")
                        //startRecording()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    private fun startRecording() {
        if (isRecording) return

        val filePath = "${getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/whatsapp_call_${System.currentTimeMillis()}.mp4"

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(filePath)
            prepare()
            start()
        }
        isRecording = true
        Log.d("RecordWaCall", "Recording started: $filePath")
    }

    private fun stopRecording() {
        if (isRecording) {
            recorder.stop()
            recorder.release()
            isRecording = false
            Log.d("RecordWaCall", "Recording stopped")
        }
    }

    private fun startRecordingNew(
        context: Context,
        encoder: Int,
        recordingFile: String,
        audioChannels: Int,
        encodingBitrate: Int,
        audioSamplingRate: Int,
        audioSource: Int,
        mediaRecorderOutputFormat: Int,
        mediaRecorderAudioEncoder: Int,
        recordingGain: Int,
        serverRecorderListener: ServerRecorderListener
    ) {
        val realRecordingFile = CacheFileProvider.provideCacheFile(context, recordingFile)
        val realAudioSource = if (App.hasCaptureAudioOutputPermission()) {
            Log.d(TAG, "startRecording() -> hasCaptureAudioOutputPermission() is true. Changing audioSource to MediaRecorder.AudioSource.VOICE_CALL")
            MediaRecorder.AudioSource.VOICE_CALL
        } else {
            audioSource
        }

        val recorderConfig = RecorderConfig.fromPrimitives(encoder, realRecordingFile, audioChannels, encodingBitrate, audioSamplingRate, realAudioSource, mediaRecorderOutputFormat, mediaRecorderAudioEncoder, recordingGain, serverRecorderListener)
        Log.d(TAG, "startRecording() -> is recorder null ${recorder == null}, is recorder recording ${recorder?.getState() == ServerRecordingState.Recording}")
        Log.d(TAG, "startRecording() -> Calling stopRecording() just in case we have a dangling recorder. IPC communication is quite complicated and fragile.")

        recorder = when (Encoder.fromIdOrDefault(encoder)) {
            Encoder.AndroidMediaRecorder -> {
                if (CLog.isDebug()) {
                    CLog.log(logTag, "startRecording() -> This is an a normal call and encoder is AndroidMediaRecorder. Returning AndroidMediaAudioRecorder")
                }
                AndroidMediaAudioRecorder(recorderConfig)
            }

            Encoder.MediaCodec -> {
                Log.i(TAG, "startRecording() -> This is an a normal call and encoder is MediaCodec. Returning MediaCodecAudioRecorder2")
                MediaCodecAudioRecorder2(recorderConfig)
            }
        }
        recorder.startRecording()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
        //stopRecording()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}