package com.algokelvin.pinentryedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.lamonjush.pinentryedittext.PinEntryEditText;
import com.lamonjush.pinentryedittext.PinEntryListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PinEntryEditText pinEntryEditText = findViewById(R.id.pinEntryEditText);
        pinEntryEditText.setPinEntryListener(
                new PinEntryListener() {
                    @Override
                    public void onPinEntered(String pin) {
                        Toast.makeText(MainActivity.this,pin,Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}