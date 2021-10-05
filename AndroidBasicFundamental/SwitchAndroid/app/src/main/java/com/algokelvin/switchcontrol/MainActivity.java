package com.algokelvin.switchcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchAndroid = findViewById(R.id.switch_android);
        TextView txtCondition = findViewById(R.id.txt_condition);

        switchAndroid.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                txtCondition.setText("Switch is ON");
                txtCondition.setTextColor(getResources().getColor(R.color.green));
            } else {
                txtCondition.setText("Switch is OFF");
                txtCondition.setTextColor(getResources().getColor(R.color.red));
            }
        });
    }
}