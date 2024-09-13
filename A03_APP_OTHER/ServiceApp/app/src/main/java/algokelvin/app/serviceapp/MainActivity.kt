package algokelvin.app.serviceapp

import algokelvin.app.serviceapp.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        serviceIntent.putExtra("NAME", "AlgoKelvin")
        serviceIntent.putExtra("NUMBER", 1000)

        binding.btnStart.setOnClickListener {
            Log.i("ALGOKELVIN", "Starting Service")
            startService(serviceIntent)
        }

        binding.btnStop.setOnClickListener {
            Log.i("ALGOKELVIN", "Stopping Service")
            stopService(serviceIntent)
        }

    }
}