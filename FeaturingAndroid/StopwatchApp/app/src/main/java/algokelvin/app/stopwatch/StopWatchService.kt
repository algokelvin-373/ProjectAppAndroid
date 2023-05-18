package algokelvin.app.stopwatch

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*
import kotlin.math.roundToInt

class StopWatchService: Service() {

    companion object {
        const val CURRENT_TIME = "CURRENT_TIME"
        const val UPDATE_TIME = "UPDATE_TIME"
    }

    override fun onBind(p0: Intent?): IBinder? = null
    private var timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val time = intent.getDoubleExtra(CURRENT_TIME, 0.0)
        timer.scheduleAtFixedRate(StopWatchTimerTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class StopWatchTimerTask(private var time: Double): TimerTask() {
        override fun run() {
            val intent = Intent(UPDATE_TIME)
            intent.putExtra(CURRENT_TIME, ++time)
            sendBroadcast(intent)
        }
    }

}