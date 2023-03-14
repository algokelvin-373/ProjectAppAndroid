package com.algokelvin.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnReady = findViewById(R.id.btn_ready);
        btnReady.setOnClickListener(v -> {
            Intent intentReady = new Intent(this, TransactionActivity.class);
            startActivity(intentReady);
        });

    }
}