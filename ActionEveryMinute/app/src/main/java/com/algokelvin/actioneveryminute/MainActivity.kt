package com.algokelvin.actioneveryminute

import com.algokelvin.actioneveryminute.databinding.ActivityMainBinding
import java.util.*

class MainActivity : BindingActivity<ActivityMainBinding>(), UiThreadInterface {
    private var timer: Timer? = null
    private var times = 1

    override fun contentView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun mainUI() {
        binding.btnStart.setOnClickListener {
            startTimer()
        }
        binding.btnStop.setOnClickListener {
            stopTimer()
        }
        binding.btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (timer != null) return

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    uiThread()
                }
            }
        }, 5000, 5000)
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
    }

    private fun resetTimer() {
        stopTimer()
        times = 1
        binding.txtRun.text = getString(R.string.initial_counter)
    }

    override fun uiThread() {
        binding.txtRun.text = getString(R.string.counter_format, times++)
    }

    override fun onDestroy() {
        stopTimer()
        super.onDestroy()
    }

}
