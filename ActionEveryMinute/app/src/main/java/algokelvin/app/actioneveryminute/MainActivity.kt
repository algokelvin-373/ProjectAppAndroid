package algokelvin.app.actioneveryminute

import algokelvin.app.actioneveryminute.databinding.ActivityMainBinding
import java.util.*

class MainActivity : BindingActivity<ActivityMainBinding>(), UiThreadInterface {
    private var times = 1

    override fun contentView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun mainUI() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    uiThread()
                }
            }
        }, 5000, 5000)
    }

    override fun uiThread() {
        binding.txtRun.text = ("${times++} Times")
    }

}