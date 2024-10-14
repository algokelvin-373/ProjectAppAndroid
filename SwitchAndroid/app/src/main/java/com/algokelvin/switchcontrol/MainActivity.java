package com.algokelvin.switchcontrol;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.switchcontrol.databinding.ActivityMainBinding;
import com.algokelvin.switchcontrol.utils.SwitchController;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SwitchController switchController = new SwitchController(this, binding.switchAndroid, binding.txtCondition);
        switchController.setTextOnOff("Switch is ON", "Switch is OFF");
        switchController.setColorOnOff(R.color.green, R.color.red);
        switchController.setActionSwitchAndroid();

    }
}