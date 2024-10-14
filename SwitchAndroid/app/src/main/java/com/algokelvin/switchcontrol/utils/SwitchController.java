package com.algokelvin.switchcontrol.utils;

import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SwitchController {
    private final AppCompatActivity appCtx;
    private final Switch switchAndroid;
    private final TextView switchTxt;
    private String txtOn, txtOff;
    private int colorOn, colorOff;

    public SwitchController(AppCompatActivity appCtx, Switch switchAndroid, TextView switchTxt) {
        this.appCtx = appCtx;
        this.switchAndroid = switchAndroid;
        this.switchTxt = switchTxt;
    }

    public void setTextOnOff(String txtSwitchOn, String txtSwitchOff) {
        this.txtOn = txtSwitchOn;
        this.txtOff = txtSwitchOff;
    }

    public void setColorOnOff(int colorSwitchOn, int colorSwitchOff) {
        this.colorOn = colorSwitchOn;
        this.colorOff = colorSwitchOff;
    }

    public void setActionSwitchAndroid() {
        switchAndroid.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchTxt.setText(txtOn);
                switchTxt.setTextColor(appCtx.getResources().getColor(colorOn));
            } else {
                switchTxt.setText(txtOff);
                switchTxt.setTextColor(appCtx.getResources().getColor(colorOff));
            }
        });
    }
}
