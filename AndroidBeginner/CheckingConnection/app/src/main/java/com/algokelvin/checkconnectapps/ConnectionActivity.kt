package com.algokelvin.checkconnectapps

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

open class ConnectionActivity: AppCompatActivity(), MyReceiver.ConnectivityReceiverListener {
    private var myReceiver: BroadcastReceiver? = null

    protected fun checkConnection() {
        myReceiver = MyReceiver()
        broadcastIntent()
    }

    private fun broadcastIntent() {
        registerReceiver(myReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()
        MyReceiver.connectivityReceiverListener = this
    }

    override fun onRestart() {
        super.onRestart()
        checkConnection()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {

    }

}