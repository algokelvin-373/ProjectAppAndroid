package com.algokelvin.wizardpos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.algokelvin.wizardpos.databinding.ActivityMainBinding
import com.cloudpos.Device
import com.cloudpos.OperationResult
import com.cloudpos.POSTerminal
import com.cloudpos.TimeConstants
import com.cloudpos.msr.MSRDevice

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
            runOnUiThread {
                Log.i("WizardPos", statusOnDevice.toString())
                val threadDetectCard = DetectCardThread()
                threadDetectCard.start()
                Log.i("WizardPos", statusOnDevice.toString())
            }
        }
    }
    private inner class DetectCardThread : Thread() {
        override fun run() {
            try {
                device.open()
                statusOnDevice = 1
                Log.i("WizardPos", "run: MSR is running")
                val operationResult = (device as MSRDevice).waitForSwipe(TimeConstants.FOREVER)
                if (operationResult.resultCode == OperationResult.SUCCESS) {
                    val trackData = operationResult.msrTrackData

                    // This is define to data Credit Card
                    val keyCreditCard0 = String(trackData.getTrackData(0))
                    val keyCreditCard1 = String(trackData.getTrackData(1))

                    // This is section to Determine Name and Deadline Credit Card
                    mainBinding.dataCard0.text = ("dataCard 0 : $keyCreditCard0")
                    if (trackData.getTrackData(0) != null) {
                        Log.i("WizardPos","dataCard 0 : $keyCreditCard0")
                    }
                    else {
                        Log.i("WizardPos","dataCard 0 : Kosong")
                    }

                    // This is to get code and CVV Credit Card
                    mainBinding.dataCard1.text = ("dataCard 1 : $keyCreditCard1")
                    if (trackData.getTrackData(1) != null) {
                        Log.i("WizardPos","dataCard 1 : $keyCreditCard1")
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.i("WizardPos","dataCard : ${e.message}")
            }
            finally {
                try {
                    device.close()
                    Log.i("WizardPos", "run: MSR Mati")
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}