package com.algokelvin.keyboard.simulator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainJavaActivity extends KeyboardController {
    private ImageView[] crossData;
    private ConstraintLayout[] clData;
    private TextView[] txtData;
    private ConstraintLayout clBefore , clNow;
    private ImageView crossBefore, crossNow;
    private TextView txtBefore, txtNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] clIntData = new int[]{
                R.id.cl_first_name, R.id.cl_second_name, R.id.cl_third_name
        };
        clData = new ConstraintLayout[clIntData.length];
        for (int x = 0; x < clData.length; x++) {
            clData[x] = findViewById(clIntData[x]);
        }

        int[] crossIntData = new int[]{
                R.id.cross_first_name, R.id.cross_second_name, R.id.cross_third_name
        };
        crossData = new ImageView[crossIntData.length];
        for (int x = 0; x < crossData.length; x++) {
            crossData[x] = findViewById(crossIntData[x]);
        }

        int[] txtIntData = new int[]{
                R.id.first_name, R.id.second_name, R.id.third_name
        };
        txtData = new TextView[txtIntData.length];
        for (int x = 0; x < txtData.length; x++) {
            txtData[x] = findViewById(txtIntData[x]);
        }

        clBefore = clData[0];
        clNow = clData[0];
        crossBefore = crossData[0];
        crossNow = crossData[0];
        txtBefore = txtData[0];
        txtNow = txtData[0];

        setControllerInputData(R.id.first_name);
        setImgCrossInput(R.id.cross_first_name);
        setControllerKeyboard();
        setKeyboardOnClickListener();

        clData[0].setOnClickListener (v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(clData[0], crossData[0], txtData[0]);
            setTextBefore(txtData[0].getText().toString());
        });

        clData[1].setOnClickListener (v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(clData[1], crossData[1], txtData[1]);
            setTextBefore(txtData[1].getText().toString());
        });

        clData[2].setOnClickListener(v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(clData[2], crossData[2], txtData[2]);
            setTextBefore(txtData[2].getText().toString());
        });

    }

    private void setConstrainLayoutTextView(ConstraintLayout cl) {
        clBefore.setBackgroundResource(R.drawable.bg_menu_white);
        clNow = cl;
        clNow.setBackgroundResource(R.drawable.bg_menu_grey);
        clBefore = clNow;
    }
    private void setCrossTextView(ImageView cross) {
        crossBefore.setVisibility(View.GONE);
        crossNow = cross;
        crossNow.setVisibility(View.VISIBLE);
        crossBefore = crossNow;
        setImgCrossInput(crossNow.getId());
    }
    private void setActionTextKeyboardSimulator(ConstraintLayout cl, ImageView cross, TextView txt) {
        setConstrainLayoutTextView(cl);
        setCrossTextView(cross);
        if (txtBefore != null)
            setTextBefore(txtBefore.getText().toString());
        txtNow = txt;
        txtBefore = txtNow;
        setControllerInputData(txt.getId());
    }

}