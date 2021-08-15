package com.algokelvin.timer

import android.os.CountDownTimer
import android.view.View
import android.widget.TextView

class TimerKotlin {
    companion object {
        @kotlin.jvm.JvmStatic
        fun createTimer(tvTimer: TextView, statusTimeOut: TextView, delay: Int, maxLoop: Int) {
            val timer: CountDownTimer = object : CountDownTimer(delay.toLong() * maxLoop, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val minutes = millisUntilFinished / 1000 / 60
                    val seconds = millisUntilFinished / 1000 % 60
                    tvTimer.text = ("0$minutes:0$seconds")
                }

                override fun onFinish() {
                    statusTimeOut.visibility = View.VISIBLE
                    tvTimer.text = ("00:00")
                }
            }
            timer.start()
        }

        annotation class JvmStatic
    }
}