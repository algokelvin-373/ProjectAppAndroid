package com.tsm.printmanagerurovo

import android.annotation.SuppressLint
import android.device.PrinterManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        const val PRINT_TEXT = 0
        const val PRINT_BITMAP = 1
        const val PRINT_CUSTOM = 2
        const val PRNSTS_OK = 0
        const val PRNSTS_OUT_OF_PAPER = -1 //Out of paper
        const val PRNSTS_OVER_HEAT = -2 //Over heat
        const val PRNSTS_UNDER_VOLTAGE = -3 //under voltage
        const val PRNSTS_BUSY = -4 //Device is busy
        const val PRNSTS_ERR = -256 //Common error
        const val PRNSTS_ERR_DRIVER = -257 //Printer Driver error
    }
    private var mPrintHandler: Handler? = null
    private lateinit var mFontStylePanel: FontStylePanel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomThread().start()
        mFontStylePanel = FontStylePanel(this)

        btn_print.setOnClickListener {
            contentPrint()
        }
        btn_print_02.setOnClickListener {
            mFontStylePanel.fontName = "Roboto"
            mFontStylePanel.fontSize = 30
            contentPrint()
        }
        btn_print_03.setOnClickListener {
            mFontStylePanel.fontName = "Roboto"
            mFontStylePanel.fontSize = 15
            contentPrint()
        }
        btn_print_04.setOnClickListener {
            //Print a default picture
            val opts = BitmapFactory.Options()
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888
            opts.inDensity = resources.displayMetrics.densityDpi
            opts.inTargetDensity = resources.displayMetrics.densityDpi
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_logo, opts)
            val msg = mPrintHandler?.obtainMessage(PRINT_BITMAP)
            msg?.obj = bitmap
            msg?.sendToTarget()
        }
        btn_print_05.setOnClickListener {
            contentPrintTwo()
        }
    }
    private fun contentPrint() {
        val content = """
                    Print test content! One
                    """.trimIndent()
        val msg: Message? = mPrintHandler?.obtainMessage(PRINT_TEXT)
        msg?.obj = content
        msg?.sendToTarget()
    }
    private fun contentPrintTwo() {
        val content = ArrayList<CustomPrint>()
        content.add(CustomPrint("Print test 1", "Roboto", 15))
        content.add(CustomPrint("", "Roboto", 15))
        content.add(CustomPrint("Print test 2", "Arial", 20))
        content.add(CustomPrint("", "Roboto", 15))
        content.add(CustomPrint("Print test 3", "Times New Roman", 25))
        content.add(CustomPrint("1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKL", "Courier", 15))
        content.add(CustomPrint("1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKL", "Courier", 20))
        content.add(CustomPrint("1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKL", "Courier", 25))
        val msg: Message? = mPrintHandler?.obtainMessage(PRINT_CUSTOM)
        msg?.obj = content
        msg?.sendToTarget()
    }

    internal inner class CustomThread : Thread() {
        override fun run() {
            Looper.prepare()
            mPrintHandler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    when (msg.what) {
                        PRINT_TEXT, PRINT_BITMAP -> {
                            doPrint(PrinterManager(), msg.what, msg.obj)
                        }
                        PRINT_CUSTOM -> {
                            doPrintCustom(PrinterManager(), msg.obj)
                        }
                    }
                }
            }
            Looper.loop() //4.Start message loop
        }
    }

    private fun doPrint(printerManager: PrinterManager, type: Int, content: Any) {
        var ret = printerManager.status //Get printer status
        if (ret == PRNSTS_OK) {
            printerManager.setupPage(384, -1) //Set paper size
            when (type) {
                PRINT_TEXT -> {
                    val fontSize = mFontStylePanel.fontSize
                    val fontName: String? = mFontStylePanel.fontName
                    var height = 0
                    val texts = (content as String).split("\n".toRegex()).toTypedArray() //Split print content into multiple lines
                    for (text in texts) {
                        height += printerManager.drawText(text, 0, height, fontName, fontSize, false, false, 0) //Printed text
                    }
                }
                PRINT_BITMAP -> {
                    val bitmap = content as Bitmap
                    printerManager.drawBitmap(bitmap, 50, 0)
                }
            }
            ret = printerManager.printPage(0) //Execution printing
            printerManager.paperFeed(16) //paper feed
        }
        updatePrintStatus(ret)
    }
    private fun doPrintCustom(printerManager: PrinterManager, content: Any) {
        var ret = printerManager.status //Get printer status
        if (ret == PRNSTS_OK) {
            printerManager.setupPage(384, -1) //Set paper size

            val data = content as ArrayList<CustomPrint>
            var height = 0
            for (i in data) {
                val texts = (i.text).split("\n".toRegex()).toTypedArray() //Split print content into multiple lines
                for (text in texts) {
                    height += if (text.isEmpty()) {
                        16
                    } else {
                        printerManager.drawText(text, 0, height, i.fontName, i.fontSize, false, false, 0) //Printed text
                    }
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