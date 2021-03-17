package com.algokelvin.wizardpos

import android.util.Log
import com.cloudpos.Device
import com.cloudpos.OperationResult
import com.cloudpos.TimeConstants
import com.cloudpos.msr.MSRDevice

class DetectCardThread(private var device: Device, private var statusOnDevice: Int) : Thread() {
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
                if (trackData.getTrackData(0) != null) {
                    Log.i("WizardPos","dataCard 0 : $keyCreditCard0")
                }
                else {
                    Log.i("WizardPos","dataCard 0 : Kosong")
                }

                // This is to get code and CVV Credit Card
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
                Log.i("WizardPos","dataCard : ${e.message}")
            }
        }
    }
}