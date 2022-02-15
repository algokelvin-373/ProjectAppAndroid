package com.algokelvin.checkconnectapps

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.algokelvin.checkconnectapps.databinding.ActivityMainBinding

class MainActivity: ConnectionImpl() {
    private lateinit var binding: ActivityMainBinding
    private var isConnection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            initView()
        }
    }

    private fun isNetworkConnected(ctx: Context): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun ActivityMainBinding.initView() {
        checkConnection()
        btnSetNetwork.setOnClickListener { setConnection() }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnection = isConnected
        binding.apply {
            showNetworkMessage(isConnected)
        }
    }

    private fun setConnection() {
        if (!isConnection) {
            val intentToSetNetwork = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intentToSetNetwork)
        } else {
            binding.layoutStatusConnect.visibility = View.GONE
        }
    }

    private fun ActivityMainBinding.showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            layoutStatusConnect.visibility = View.VISIBLE
            txtResultError.text = ("Network is OFF")
            btnSetNetwork.text = ("Setting Network")
        } else {
            txtResultError.text = ("Network is ON")
            btnSetNetwork.text = ("Back")
        }
    }

}