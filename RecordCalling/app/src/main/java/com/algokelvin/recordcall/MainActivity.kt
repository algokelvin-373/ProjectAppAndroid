package com.algokelvin.recordcall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.algokelvin.recordcall.service.CallRecorderService

class MainActivity : AppCompatActivity() {
    private val TAG = "RecordCallingLogger"
    private lateinit var handler: Handler
    private lateinit var btnRecord: Button
    private lateinit var btnRecordWhatsApp: Button
    private var isRecording = false
    private var isInCall = false

    private val PERMISSIONS = arrayOf(
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.PROCESS_OUTGOING_CALLS,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val callReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i(TAG, "Broadcast received: ${intent?.getBooleanExtra("isInCall", false)}")
            Log.d(TAG, "Menerima broadcast: ${intent?.action}")
            val isInCall = intent?.getBooleanExtra("isInCall", false) ?: false
            Log.d(TAG, "Received call state: $isInCall")

            runOnUiThread {
                btnRecordWhatsApp.isEnabled = isInCall
                btnRecordWhatsApp.text = if (isInCall) "RECORD" else "DISABLED"

                // Untuk debugging UI
                btnRecordWhatsApp.setBackgroundColor(
                    if (isInCall) Color.GREEN else Color.RED
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())
        btnRecord = findViewById(R.id.btnRecord)
        checkPermissions()
        setupCallDetector()

        btnRecord.setOnClickListener {
            if(isRecording) {
                stopRecording()
            } else {
                startRecording()
            }
        }

        btnRecordWhatsApp = findViewById(R.id.btnRecordWhatsApp)
        checkAccessibilityPermission()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(callReceiver, IntentFilter("WHATSAPP_CALL_STATE_CHANGED"))
    }

    /*override fun onResume() {
        super.onResume()
        checkCurrentCallState()
    }

    private fun checkCurrentCallState() {
        btnRecordWhatsApp.isEnabled = isInCall
    }*/

    override fun onResume() {
        super.onResume()
        startPolling() // Mulai polling saat activity aktif
    }

    override fun onPause() {
        super.onPause()
        stopPolling()
    }

    private fun startPolling() {
        handler.postDelayed(pollingRunnable, 1000)
    }

    private fun stopPolling() {
        handler.removeCallbacks(pollingRunnable)
    }

    private val pollingRunnable = object : Runnable {
        override fun run() {
            checkForcedCallState()
            handler.postDelayed(this, 1000) // Polling setiap 1 detik
        }
    }

    private fun checkForcedCallState() {
        runOnUiThread {
            btnRecordWhatsApp.isEnabled = true
            btnRecordWhatsApp.text = "RECORD"
            btnRecordWhatsApp.setBackgroundColor(Color.GREEN)
        }
    }

    private fun checkPermissions() {
        if (PERMISSIONS.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 101)
        }
    }

    private fun checkAccessibilityPermission() {
        val accessibilityEnabled = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED) == 1
        if (!accessibilityEnabled) {
            AlertDialog.Builder(this)
                .setTitle("Akses Diperlukan")
                .setMessage("Aktifkan aksesibilitas untuk mendeteksi panggilan WhatsApp")
                .setPositiveButton("Buka Pengaturan") { _, _ ->
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                }.show()
        }
    }

    private fun setupCallDetector() {
        Log.i(TAG, "setupCallDetector")
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                when(state) {
                    TelephonyManager.CALL_STATE_OFFHOOK -> {
                        Log.i(TAG, "setupCallDetector - CALL_STATE_OFFHOOK")
                        isInCall = true
                        btnRecord.isEnabled = true
                    }
                    TelephonyManager.CALL_STATE_IDLE -> {
                        Log.i(TAG, "setupCallDetector - CALL_STATE_IDLE")
                        isInCall = false
                        if(isRecording) {
                            stopRecording()
                        }
                        btnRecord.isEnabled = false
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private fun startRecording() {
        Log.i(TAG, "MainActivity - startRecording")
        val intent = Intent(this, CallRecorderService::class.java).apply {
            action = "START_RECORDING"
        }
        startService(intent)
        isRecording = true
        btnRecord.text = "Stop Rekam"
    }

    private fun stopRecording() {
        Log.i(TAG, "MainActivity - stopRecording")
        val intent = Intent(this, CallRecorderService::class.java).apply {
            action = "STOP_RECORDING"
        }
        startService(intent)
        isRecording = false
        btnRecord.text = "Mulai Rekam"
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(callReceiver)
    }
}