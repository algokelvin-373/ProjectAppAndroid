package com.algokelvin.checkconnectapps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var status: String? = NetworkUtil().getConnectivityStatusString(context)
        if (status?.isEmpty()!!) {
            status = "No Internet Connection"
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show()
    }
}