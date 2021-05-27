package com.algokelvin.jobscheduller.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        new Thread(() -> {
            // a potentially time consuming task
            Log.i("thread-run", "Thread run");
            txt = "Thread Run Post";
            txtHello.setText(txt);
        }).start();
    }
}