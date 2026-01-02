package com.algokelvin.uitoast

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.algokelvin.uitoast.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToast.setOnClickListener { showWithToast() }
        binding.btnSnackBar.setOnClickListener { showWithSnackBar() }
    }

    private fun showWithToast() {
        Toast.makeText(this, "Show with Toast", Toast.LENGTH_LONG).show()
    }

    private fun showWithSnackBar() {
        Snackbar.make(binding.coordinatorLayout, "Show with SnackBar", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

}