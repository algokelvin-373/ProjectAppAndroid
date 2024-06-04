package com.algokelvin.generate.qr;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Map;

import kotlin.TuplesKt;
import kotlin.collections.MapsKt;

public class GenerateQRJava {
    public static void createBarcode(ImageView img_barcode, MultiFormatWriter multiFormatWriter, String data) throws WriterException {
        Map hintMap = MapsKt.mapOf(TuplesKt.to(EncodeHintType.MARGIN, 0));
        BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 400, 400, hintMap);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        img_barcode.setImageBitmap(bitmap);
    }
}
