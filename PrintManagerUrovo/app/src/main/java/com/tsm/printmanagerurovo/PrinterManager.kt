package com.tsm.printmanagerurovo

import android.content.Context
import android.device.PrinterManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.*

fun getPrinterManager(mPrinterManager: PrinterManager): PrinterManager {
    mPrinterManager.open()
    return mPrinterManager
}
fun Context.getPrintBitmap(): Bitmap {
    val opts = BitmapFactory.Options()
    opts.inPreferredConfig = Bitmap.Config.ARGB_8888
    opts.inDensity = resources.displayMetrics.densityDpi
    opts.inTargetDensity = resources.displayMetrics.densityDpi
    return BitmapFactory.decodeResource(resources, R.drawable.ic_logo, opts)
}
fun printCustom(printerManager: PrinterManager, content: Any) {
    printerManager.setupPage(384, -1) //Set paper size

    val data = content as ArrayList<Any>
    var height = 0
    for (i in data) {
        if (i is Bitmap) {
            printerManager.drawBitmap(i, 50, 0) //Printed Logo
            height += 50
        } else {
            val texts = ((i as CustomPrint).text).split("\n".toRegex()).toTypedArray() //Split print content into multiple lines
            for (text in texts) {
                height += when {
                    text.isEmpty() -> 16 //Make Space
                    else -> printerManager.drawText(text, 0, height, i.fontName, i.fontSize, false, false, 0) //Printed text
                }
            }
        }
    }
}

data class CustomPrint(
        val text: String,
        val fontName: String,
        val fontSize: Int
)