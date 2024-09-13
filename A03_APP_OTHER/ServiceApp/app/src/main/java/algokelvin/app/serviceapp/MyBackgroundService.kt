package algokelvin.app.serviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBackgroundService: Service() {

    init {
        Log.i("ALGOKELVIN", "Service has been created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("ALGOKELVIN", "Service started")
        val name = intent?.getStringExtra("NAME")
        val number = intent?.getIntExtra("NUMBER", 0)
        Log.i("ALGOKELVIN", "Name: "+ name +" - Number: "+ number)
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        Log.i("ALGOKELVIN", "Destroying...")
        super.onDestroy()
    }

}