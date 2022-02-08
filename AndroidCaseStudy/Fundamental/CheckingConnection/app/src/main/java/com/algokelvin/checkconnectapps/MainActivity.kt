package com.algokelvin.checkconnectapps

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.checkconnectapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyReceiver.ConnectivityReceiverListener {
    private lateinit var binding: ActivityMainBinding
    private var myReceiver: BroadcastReceiver? = null
    private var isConnection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnection()

    }
    private fun broadcastIntent() {
        registerReceiver(myReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun checkConnection() {
        myReceiver = MyReceiver()
        broadcastIntent()
    }

    override fun onResume() {
        super.onResume()
        Log.i("connection", "On Resume")
        MyReceiver.connectivityReceiverListener = this
    }

    override fun onRestart() {
        super.onRestart()
        checkConnection()
        Log.i("connection", "On Restart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("connection", "On Pause")
        unregisterReceiver(myReceiver)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            66 -> setConnection()
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun setConnection() {
        if (!isConnection) {
            val intentToSetNetwork = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intentToSetNetwork)
        } else {
            finish()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnection = isConnected
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            binding.txtResultError.text = ("Network is OFF")
            binding.btnBackMenuError.text = ("Setting Network")
        } else {
            binding.txtResultError.text = ("Network is ON")
            binding.btnBackMenuError.text = ("Kembali")
        }
    }
}
