package com.algokelvin.generate.qr

import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class GenerateQRKotlin {
    companion object {
        @kotlin.jvm.JvmStatic
        fun createBarcode(img_barcode: ImageView, multiFormatWriter: MultiFormatWriter, data: String) {
            val hintMap = mapOf(EncodeHintType.MARGIN to 0) // style remove white space
            val bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 400, 400, hintMap)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)

            img_barcode.setImageBitmap(bitmap)
        }
    }
}