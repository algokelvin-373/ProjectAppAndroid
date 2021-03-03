package com.tsm.printmanagerurovo

import android.device.PrinterManager

fun getPrinterManager(mPrinterManager: PrinterManager): PrinterManager {
    mPrinterManager.open()
    return mPrinterManager
}

data class CustomPrint(
        val text: String,
        val fontName: String,
        val fontSize: Int
)