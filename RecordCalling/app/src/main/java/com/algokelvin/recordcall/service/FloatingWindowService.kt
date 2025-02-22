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

            // Di dalam method startRecordingWhatsAppCall()
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "RECORD_AUDIO permission not granted")
                return
            }

            // 1. Audio configuration
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            val result = audioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_VOICE_CALL,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )

            if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.e(TAG, "Audio focus not granted")
                return
            }
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
            )

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
            val bufferSize = minBufferSize * 4
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
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
        var randomAccessFile: RandomAccessFile? = null
        var totalBytes = 0L

        try {
            val file = File(filePath)
            randomAccessFile = RandomAccessFile(file, "rw")

            // Tulis header sementara
            writeWavHeader(randomAccessFile, 44100, 16, 1, 0)

            // Mulai rekaman
            audioRecord?.startRecording()

            while (isRecording) {
                val bytesRead = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (bytesRead > 0) {
                    randomAccessFile.write(buffer, 0, bytesRead)
                    totalBytes += bytesRead
                }
            }

            // Update header dengan ukuran sebenarnya
            randomAccessFile.seek(0)
            writeWavHeader(randomAccessFile, 44100, 16, 1, totalBytes.toInt())

        } catch (e: Exception) {
            Log.e(TAG, "Recording error: ${e.stackTraceToString()}")
        } finally {
            randomAccessFile?.close()
        }
    }

    private fun writeWavHeader(
        raf: RandomAccessFile,
        sampleRate: Int,
        bitsPerSample: Int,
        channels: Int,
        dataSize: Int
    ) {
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val blockAlign = channels * bitsPerSample / 8

        raf.write("RIFF".toByteArray())
        raf.write(intToByteArray(36 + dataSize))  // ChunkSize
        raf.write("WAVE".toByteArray())
        raf.write("fmt ".toByteArray())
        raf.write(intToByteArray(16))             // Subchunk1Size
        raf.write(shortToByteArray(1))            // AudioFormat (PCM)
        raf.write(shortToByteArray(channels.toShort()))
        raf.write(intToByteArray(sampleRate))
        raf.write(intToByteArray(byteRate))
        raf.write(shortToByteArray(blockAlign.toShort()))
        raf.write(shortToByteArray(bitsPerSample.toShort()))
        raf.write("data".toByteArray())
        raf.write(intToByteArray(dataSize))       // Subchunk2Size
    }

    // Helper functions
    private fun intToByteArray(i: Int): ByteArray {
        return byteArrayOf(
            (i and 0xFF).toByte(),
            (i shr 8 and 0xFF).toByte(),
            (i shr 16 and 0xFF).toByte(),
            (i shr 24 and 0xFF).toByte()
        )
    }

    private fun shortToByteArray(s: Short): ByteArray {
        return byteArrayOf(
            (s.toInt() and 0xFF).toByte(),
            (s.toInt() shr 8 and 0xFF).toByte()
        )
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