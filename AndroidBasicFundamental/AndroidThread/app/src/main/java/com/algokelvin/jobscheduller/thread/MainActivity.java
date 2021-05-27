package com.algokelvin.jobscheduller.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String txt = "empty data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtHello = findViewById(R.id.txt_hello);
        txtHello.setText(txt);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Log.d("Handler", "Running Handler");
            Log.d("Handler", "Running Success");
            txt = "Handle Run Success";
            txtHello.setText(txt);
        }, 2500);
    }
}