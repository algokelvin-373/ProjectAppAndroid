package com.tsm.printmanagerurovo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val PRINT_TEXT = 0
    }
    private val mPrintHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_print.setOnClickListener {
            val content = """
                    Print test content!
                    0123456789
                    abcdefghijklmnopqrstuvwsyz
                    ABCDEFGHIJKLMNOPQRSTUVWSYZ
                    """.trimIndent()

            val msg: Message? = mPrintHandler?.obtainMessage(PRINT_TEXT)
            msg?.obj = content
            msg?.sendToTarget()
        }
    }
}