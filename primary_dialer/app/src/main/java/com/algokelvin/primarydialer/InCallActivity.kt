package com.algokelvin.primarydialer

import android.Manifest.permission
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.os.SystemClock
import android.telecom.Call
import android.telecom.TelecomManager
import android.util.Log
import android.view.WindowManager
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.primarydialer.databinding.ActivityInCallBinding

class InCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInCallBinding
    //private lateinit var currentCall: Call
    private lateinit var audioProcessor: AudioProcessingManager
    private lateinit var audioManager: AudioManager
    private lateinit var callTimer: Chronometer

    private val tag = "TEST_CALL"
    private var isMicMuted = false;
    private var isSpeakerOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Keep screen on during call
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Get the phone number from the intent
        val phoneNumber = intent.getStringExtra("phone_number")
        if (!phoneNumber.isNullOrEmpty()) {
            binding.phoneNumberText.text = phoneNumber
        }

        setupButtonListeners()
        setupAudioManager()
        setupCall()
    }

    private fun setupButtonListeners() {
        binding.toggleMicButton.setOnClickListener {
            isMicMuted = !isMicMuted
        }
        binding.toggleSpeakerButton.setOnClickListener {
            isSpeakerOn = !isSpeakerOn;
            audioManager.isSpeakerphoneOn = isSpeakerOn
        }
        binding.endCallButton.setOnClickListener {
            /*if (currentCall != null) {
                currentCall.disconnect()
            }*/
        }
    }

    private fun setupAudioManager() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        audioProcessor = AudioProcessingManager()
    }

    private fun setupCall() {
        val callId = intent.getStringExtra("call_id")
        val telecomManager = getSystemService(TELECOM_SERVICE) as TelecomManager

        if (telecomManager != null) {
            /*for (Call call : telecomManager.getCalls()) {
                if (call.getDetails().getTelecomCallId().equals(callId)) {
                    currentCall = call;
                    break;
                }
            }*/
        }

        /*if (currentCall != null) {
            updateCallInfo()
            // Register callback
            currentCall.registerCallback(object : Call.Callback() {
                override fun onStateChanged(call: Call, state: Int) {
                    super.onStateChanged(call, state)
                    runOnUiThread { handleCallStateChanged(state) }
                }
            })
            startAudioProcessing()
        }*/
    }

    private fun updateCallInfo() {
        /*val uri = currentCall.details.handle
        if (uri != null) {
            val phoneNumber = uri.schemeSpecificPart
            binding.phoneNumberText.text = phoneNumber
        }*/
    }

    private fun handleCallStateChanged(state: Int) {
        when(state) {
            Call.STATE_ACTIVE -> {
                Log.i(tag, "Into state active")
                binding.callStatusText.text = "Connected"
                callTimer.base = SystemClock.elapsedRealtime()
                callTimer.start()
            }
            Call.STATE_DISCONNECTED -> {
                Log.i(tag, "Into state disconnected")
                finish()
            }
        }
    }

    private fun startAudioProcessing() {
        if (checkSelfPermission(permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            audioProcessor.startProcessing()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (audioProcessor != null) {
            audioProcessor.stopProcessing()
        }
        /*if (currentCall != null) {
//            currentCall.unregisterCallback();
        }*/
    }
}