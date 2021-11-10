package com.algokelvin.switchcontrol;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.switchcontrol.utils.SwitchController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchController switchController = new SwitchController(this, R.id.switch_android, R.id.txt_condition);
        switchController.setTextOnOff("Switch is ON", "Switch is OFF");
        switchController.setColorOnOff(R.color.green, R.color.red);
        switchController.setActionSwitchAndroid();

    }
}