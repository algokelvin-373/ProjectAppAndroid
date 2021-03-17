package com.algokelvin.wizardpos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.wizardpos.databinding.ActivityMainBinding
import com.cloudpos.Device
import com.cloudpos.POSTerminal

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var device : Device

    private var statusOnDevice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        device = POSTerminal.getInstance(this).getDevice("cloudpos.device.msr")
        if (statusOnDevice == 0) {
            Log.i("WizardPos", statusOnDevice.toString())
            val threadDetectCard = DetectCardThread(device, statusOnDevice)
            threadDetectCard.start()
            Log.i("WizardPos", statusOnDevice.toString())
        }
    }
}