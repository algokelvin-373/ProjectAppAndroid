package algokelvin.app.network_connect

import algokelvin.app.network_connect.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View

class MainActivity : ConnectionController() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNetworkConnected()
        setConnection()
    }

    private fun setConnection() {
        if (!isConnected) {
            binding.txtMsgConnection.text = ("Connection is Off")
            binding.btnSetNetwork.text = ("Setting Network")
            binding.btnSetNetwork.setOnClickListener {
                val intentToSetNetwork = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intentToSetNetwork)
            }
        } else {
            binding.layoutStatusConnect.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        isNetworkConnected()
        setConnection()
    }
}