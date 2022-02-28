package com.algokelvin.checkconnectapps

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import com.algokelvin.checkconnectapps.databinding.ActivityConnectionBinding

class ConnectionActivity : ConnectionImpl() {
    private lateinit var binding: ActivityConnectionBinding
    private var isConnection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnection()
        binding.apply {
            initView()
        }

    }

    private fun ActivityConnectionBinding.initView() {
        btnSetNetwork.setOnClickListener { setConnection() }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            66 -> setConnection()
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isConnection = isConnected
        showNetworkMessage(isConnected)
    }

    private fun setConnection() {
        if (!isConnection) {
            val intentToSetNetwork = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intentToSetNetwork)
        } else {
            finish()
        }
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            binding.txtResultError.text = ("Network is OFF")
            binding.btnSetNetwork.text = ("Setting Network")
        } else {
            binding.txtResultError.text = ("Network is ON")
            binding.btnSetNetwork.text = ("Kembali")
        }
    }
}
