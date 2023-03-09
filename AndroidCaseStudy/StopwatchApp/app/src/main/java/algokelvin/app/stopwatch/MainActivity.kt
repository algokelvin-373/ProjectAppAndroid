package algokelvin.app.stopwatch

import algokelvin.app.stopwatch.databinding.ActivityMainBinding
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var serviceIntent: Intent

    private var isStarted = false
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startOrStop()
        }
        binding.btnReset.setOnClickListener {
            reset()
        }

        serviceIntent = Intent(applicationContext, StopWatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopWatchService.UPDATE_TIME))

    }

    private fun startOrStop() {
        if (isStarted)
            stop()
        else
            start()
    }

    private fun start() {
        serviceIntent.putExtra(StopWatchService.CURRENT_TIME, time)
        startService(serviceIntent)
        binding.btnStart.text = "Stop"
        isStarted = true
    }

    private fun stop() {
        stopService(serviceIntent)
        binding.btnStart.text = "Start"
        isStarted = false
    }

    private fun reset() {
        stop()
        time = 0.0
        binding.txtTime.text = getFormattedTime(time)
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(StopWatchService.CURRENT_TIME, 0.0)
            binding.txtTime.text = getFormattedTime(time)
        }
    }

    private fun getFormattedTime(time: Double): String {
        val timeInt = time.roundToInt()
        val hours = timeInt % 86400 / 3600
        val minutes = timeInt % 86400 % 3600 / 60
        val seconds = timeInt % 86400 % 3600 % 60
        return String.format("%02d:%02d:%02", hours, minutes, seconds)
    }

}