package com.algokelvin.jobscheduller.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String txt = "empty data";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtHello = findViewById(R.id.txt_hello);
        txtHello.setText(txt);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                if (count++ < 5) {
                    txtHello.setText(String.valueOf(count));
                    handler.postDelayed(this, 2500); // WAIT 2.5 SECONDS
                } else {
                    handler.removeCallbacks(this);
                    txt = "Finish";
                    txtHello.setText(txt);
                    // FINISH REAL
                }
                // CONTINUE
            }
        };
        handler.post(runnable); // START
        Log.d("HandlerPost", "---START---");
    }
}