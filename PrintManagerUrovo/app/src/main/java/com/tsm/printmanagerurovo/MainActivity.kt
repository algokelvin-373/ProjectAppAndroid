package com.tsm.printmanagerurovo

import android.annotation.SuppressLint
import android.device.PrinterManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val PRINT_TEXT = 0
        const val PRNSTS_OK = 0
        const val PRNSTS_OUT_OF_PAPER = -1 //Out of paper
        const val PRNSTS_OVER_HEAT = -2 //Over heat
        const val PRNSTS_UNDER_VOLTAGE = -3 //under voltage
        const val PRNSTS_BUSY = -4 //Device is busy
        const val PRNSTS_ERR = -256 //Common error
        const val PRNSTS_ERR_DRIVER = -257 //Printer Driver error
    }
    private var mPrintHandler: Handler? = null
    private var mPrinterManager: PrinterManager? = null
    private lateinit var mFontStylePanel: FontStylePanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomThread().start()
        mFontStylePanel = FontStylePanel(this)

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

    internal inner class CustomThread : Thread() {
        override fun run() {
            Looper.prepare()
            mPrintHandler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    when (msg.what) {
                        PRINT_TEXT -> {
                            doPrint(getPrinterManager(), msg.what, msg.obj)
                        }
                    }
                }
            }
            Looper.loop() //4.Start message loop
        }
    }

    private fun getPrinterManager(): PrinterManager {
        if (mPrinterManager == null) {
            mPrinterManager = PrinterManager()
            mPrinterManager?.open()
        }
        return mPrinterManager!!
    }

    private fun doPrint(printerManager: PrinterManager, type: Int, content: Any) {
        var ret = printerManager.status //Get printer status
        if (ret == PRNSTS_OK) {
            printerManager.setupPage(384, -1) //Set paper size
            when (type) {
                PRINT_TEXT -> {
                    val fontInfo: Bundle = mFontStylePanel.fontInfo //Get font format
                    var fontSize = 24
                    var fontStyle = 0x0000
                    var fontName: String? = "simsun"
                    if (fontInfo != null) {
                        fontSize = fontInfo.getInt("font-size", 24)
                        fontStyle = fontInfo.getInt("font-style", 0)
                        fontName = fontInfo.getString("font-name", "simsun")
                    }
                    var height = 0
                    val texts = (content as String).split("\n".toRegex()).toTypedArray() //Split print content into multiple lines
                    for (text in texts) {
                        height += printerManager.drawText(text, 0, height, fontName, fontSize, false, false, 0) //Printed text
                    }
                    for (text in texts) {
                        height += printerManager.drawTextEx(text, 5, height, 384, -1, fontName, fontSize, 0, fontStyle, 0) ////Printed text
                    }
                    height = 0
                }
            }
            ret = printerManager.printPage(0) //Execution printing
            printerManager.paperFeed(16) //paper feed
        }
        updatePrintStatus(ret)
    }

    private fun updatePrintStatus(status: Int) {
        runOnUiThread {
            when (status) {
                PRNSTS_OUT_OF_PAPER -> Toast.makeText(this, R.string.tst_info_paper, Toast.LENGTH_SHORT).show()
                PRNSTS_OVER_HEAT -> Toast.makeText(this, R.string.tst_info_temperature, Toast.LENGTH_SHORT).show()
                PRNSTS_UNDER_VOLTAGE -> Toast.makeText(this, R.string.tst_info_voltage, Toast.LENGTH_SHORT).show()
                PRNSTS_BUSY -> Toast.makeText(this, R.string.tst_info_busy, Toast.LENGTH_SHORT).show()
                PRNSTS_ERR -> Toast.makeText(this, R.string.tst_info_error, Toast.LENGTH_SHORT).show()
                PRNSTS_ERR_DRIVER -> Toast.makeText(this, R.string.tst_info_driver_error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}