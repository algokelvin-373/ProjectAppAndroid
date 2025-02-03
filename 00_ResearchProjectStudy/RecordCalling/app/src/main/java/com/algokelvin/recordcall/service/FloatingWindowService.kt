package com.algokelvin.recordcall.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.algokelvin.recordcall.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FloatingWindowService : Service() {
    private val TAG = "RecordCallingLogger"
    private var recordingFilePath: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View
    private var isRecording = false
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null

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
            Log.i(TAG, "startRecording - WA Call (AudioRecord)")

            // 1. Audio configuration
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.apply {
                mode = AudioManager.MODE_IN_COMMUNICATION
                isSpeakerphoneOn = true
                setParameters("noise_suppression=auto")
            }

            // 2. AudioRecord parameters
            val sampleRate = 44100
            val channelConfig = AudioFormat.CHANNEL_IN_MONO
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val minBufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                channelConfig,
                audioFormat
            ) * 2

            // 3. Create output file
            val outputDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: return
            if (!outputDir.exists()) outputDir.mkdirs()

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val filename = "REC_WA_${timestamp}.wav"
            val filePath = File(outputDir, filename).absolutePath

            // 4. Initialize AudioRecord
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                sampleRate,
                channelConfig,
                audioFormat,
                minBufferSize
            )

            // 5. Start recording thread
            isRecording = true
            recordingThread = Thread {
                writeAudioDataToFile(filePath)
            }.apply { start() }

        } catch (e: Exception) {
            Log.e(TAG, "Error starting recording: ${e.stackTraceToString()}")
        }
    }

    private fun writeAudioDataToFile(filePath: String) {
        val buffer = ByteArray(1024)
        var outputStream: FileOutputStream? = null
        var totalBytes = 0L

        try {
            val file = File(filePath)
            outputStream = FileOutputStream(file)

            // Tulis header sementara
            writeWavHeader(outputStream, 44100, 16, 1, 0)

            // Mulai rekaman
            audioRecord?.startRecording()

            while (isRecording) {
                val bytesRead = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytes += bytesRead
                }
            }

            // Update header dengan ukuran sebenarnya
            outputStream.channel.use {
                it.position(0)
                writeWavHeader(outputStream, 44100, 16, 1, totalBytes.toInt())
            }

        } catch (e: Exception) {
            Log.e(TAG, "Recording error: ${e.stackTraceToString()}")
        } finally {
            outputStream?.close()
        }
    }

    private fun writeWavHeader(
        out: FileOutputStream,
        sampleRate: Int,
        bitsPerSample: Int,
        channels: Int,
        dataSize: Int
    ) {
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val blockAlign = channels * bitsPerSample / 8

        // Header WAV
        val header = ByteArray(44)
        System.arraycopy("RIFF".toByteArray(), 0, header, 0, 4)
        System.arraycopy(intToByteArray(36 + dataSize), 0, header, 4, 4)
        System.arraycopy("WAVE".toByteArray(), 0, header, 8, 4)
        System.arraycopy("fmt ".toByteArray(), 0, header, 12, 4)
        System.arraycopy(intToByteArray(16), 0, header, 16, 4) // Subchunk size
        System.arraycopy(shortToByteArray(1), 0, header, 20, 2) // Audio format (PCM)
        System.arraycopy(shortToByteArray(channels.toShort()), 0, header, 22, 2)
        System.arraycopy(intToByteArray(sampleRate), 0, header, 24, 4)
        System.arraycopy(intToByteArray(byteRate), 0, header, 28, 4)
        System.arraycopy(shortToByteArray(blockAlign.toShort()), 0, header, 32, 2)
        System.arraycopy(shortToByteArray(bitsPerSample.toShort()), 0, header, 34, 2)
        System.arraycopy("data".toByteArray(), 0, header, 36, 4)
        System.arraycopy(intToByteArray(dataSize), 0, header, 40, 4)

        out.write(header)
    }

    // Helper functions
    private fun intToByteArray(i: Int): ByteArray = ByteArray(4).apply {
        this[0] = (i and 0xFF).toByte()
        this[1] = (i shr 8 and 0xFF).toByte()
        this[2] = (i shr 16 and 0xFF).toByte()
        this[3] = (i shr 24 and 0xFF).toByte()
    }

    private fun shortToByteArray(s: Short): ByteArray = ByteArray(2).apply {
        this[0] = (s.toInt() and 0xFF).toByte()
        this[1] = (s.toInt() shr 8 and 0xFF).toByte()
    }

    private fun stopRecordingWhatsAppCall() {
        Log.i(TAG, "stopRecording - WA Call")
        try {
            isRecording = false
            audioRecord?.apply {
                stop()
                release()
            }
            recordingThread?.join()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recording: ${e.stackTraceToString()}")
        } finally {
            audioRecord = null
        }
    }


    /*private fun stopRecordingWhatsAppCall() {
        Log.i(TAG, "stopRecording - WA Call")
        try {
            isRecording = false
            audioRecord?.apply {
                stop()
                release()
            }
            recordingThread?.join()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recording: ${e.message}")
        }
        audioRecord = null
    }*/

    /*private fun stopRecordingWhatsAppCall() {
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
    }*/

    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized && ::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }
}