package com.algokelvin.generate.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.MultiFormatWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgBarcode = findViewById(R.id.img_barcode);
        String bitmap = "dnjwdniwncinvinvuerhuruheuhdwudnwndwncjnjvcnejcnjencjendejndjewndjendejndje";

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        GenerateQRKotlin.createBarcode(imgBarcode, multiFormatWriter, bitmap);
    }
}