package com.algokelvin.otto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.otto.bus.AppThreadBus;

public class SecondActivity extends AppCompatActivity {
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button bUtton = findViewById(R.id.post);
        mEditText = findViewById(R.id.edtName);

        bUtton.setOnClickListener(v -> {
            String trim = mEditText.getText().toString().trim();

            // Send data 'trim' and subscribes to method 'getMessage'
            AppThreadBus.getMainThreadBusInstance().post(trim);
        });
    }
}