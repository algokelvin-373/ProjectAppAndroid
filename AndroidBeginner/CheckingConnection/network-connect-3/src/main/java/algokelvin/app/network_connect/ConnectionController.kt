package algokelvin.app.network_connect

import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

open class ConnectionController: AppCompatActivity() {
    protected var isConnected = false

    protected fun isNetworkConnected() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}