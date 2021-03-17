package com.algokelvin.checkconnectapps

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.checkconnectapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyReceiver.ConnectivityReceiverListener {
    private lateinit var binding: ActivityMainBinding
    private var myReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            Toast.makeText(this, "You're offline", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "You're online", Toast.LENGTH_LONG).show()
        }
    }
}
