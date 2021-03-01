package com.tsm.printmanagerurovo

import android.device.PrinterManager

fun getPrinterManager(mPrinterManager: PrinterManager): PrinterManager {
    mPrinterManager.open()
    return mPrinterManager
}