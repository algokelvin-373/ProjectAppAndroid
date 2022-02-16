package algokelvin.app.network_connect

import algokelvin.app.network_connect.databinding.ActivityMainBinding
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNetworkConnected()
        setConnection()
    }

    override fun onResume() {
        super.onResume()
        isNetworkConnected()
        setConnection()
    }

    private fun isNetworkConnected() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected
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
}