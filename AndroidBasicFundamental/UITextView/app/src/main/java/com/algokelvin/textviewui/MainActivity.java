package com.algokelvin.textviewui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtProgrammatically = findViewById(R.id.txt_programmatically);
        txtProgrammatically.setText("TextView Programmatically");
        txtProgrammatically.setTextSize(20.0f);
        txtProgrammatically.setTextColor(getResources().getColor(R.color.black));
        Typeface typeface = ResourcesCompat.getFont(this ,R.font.roboto_mono_thin);
        txtProgrammatically.setTypeface(typeface);

    }
}