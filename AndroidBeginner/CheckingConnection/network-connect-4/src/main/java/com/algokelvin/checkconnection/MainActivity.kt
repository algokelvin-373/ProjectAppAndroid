package com.algokelvin.checkconnection

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.algokelvin.checkconnection.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : ConnectionController() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isNetworkConnected()
        binding.btnCheckConnect.setOnClickListener { view ->
            openSnackBar(view)
        }
    }
    private fun openSnackBar(view: View) {
        if (!isConnected) {
            val snackBar = Snackbar.make(view, "Connection is Off", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            snackBar.show()
        }
    }
}