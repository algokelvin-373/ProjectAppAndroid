package com.algokelvin.keyboard.simulator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainJavaActivity extends KeyboardController {
    private ConstraintLayout clFirstName, clSecondName, clThirdName;
    private ImageView crossFirstName, crossSecondName, crossThirdName;
    private TextView txtFirstName, txtSecondName, txtThirdName;
    private String txtOne = "", txtTwo = "", txtThird = "";
    private int position = 1;
    private ConstraintLayout clBefore , clNow;
    private ImageView crossBefore, crossNow;
    private TextView txtBefore, txtNow;
    private String dataBefore, dataAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clFirstName = findViewById(R.id.cl_first_name);
        clSecondName = findViewById(R.id.cl_second_name);
        clThirdName = findViewById(R.id.cl_third_name);
        crossFirstName = findViewById(R.id.cross_first_name);
        crossSecondName = findViewById(R.id.cross_second_name);
        crossThirdName = findViewById(R.id.cross_third_name);
        txtFirstName = findViewById(R.id.first_name);
        txtSecondName = findViewById(R.id.second_name);
        txtThirdName = findViewById(R.id.third_name);

        clBefore = clFirstName;
        clNow = clFirstName;
        crossBefore = crossFirstName;
        crossNow = crossFirstName;
        txtBefore = txtFirstName;
        txtNow = txtFirstName;
        setControllerInputData(R.id.first_name);
        setImgCrossInput(R.id.cross_first_name);
        setControllerKeyboard();
        setKeyboardOnClickListener();

        clFirstName.setOnClickListener (v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(
                    R.drawable.bg_menu_grey,
                    R.drawable.bg_menu_white,
                    clFirstName,
                    View.GONE,
                    View.VISIBLE,
                    crossFirstName,
                    txtFirstName
            );
            setTextBefore(txtFirstName.getText().toString());
        });

        clSecondName.setOnClickListener (v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(
                    R.drawable.bg_menu_grey,
                    R.drawable.bg_menu_white,
                    clSecondName,
                    View.GONE,
                    View.VISIBLE,
                    crossSecondName,
                    txtSecondName
            );
            setTextBefore(txtSecondName.getText().toString());
        });

        clThirdName.setOnClickListener(v -> {
            txtBefore.setText(txtInputData.getText().toString());
            setActionTextKeyboardSimulator(
                    R.drawable.bg_menu_grey,
                    R.drawable.bg_menu_white,
                    clThirdName,
                    View.GONE,
                    View.VISIBLE,
                    crossThirdName,
                    txtThirdName
            );
            setTextBefore(txtThirdName.getText().toString());
        });

    }

    private void setConstrainLayoutTextView(int bg1, int bg2, ConstraintLayout cl) {
        clBefore.setBackgroundResource(bg2);
        clNow = cl;
        clNow.setBackgroundResource(bg1);
        clBefore = clNow;
    }
    private void setCrossTextView(int v1, int v2, ImageView cross) {
        crossBefore.setVisibility(v1);
        crossNow = cross;
        crossNow.setVisibility(v2);
        crossBefore = crossNow;
        setImgCrossInput(crossNow.getId());
    }
    private void setActionTextKeyboardSimulator(int bg1, int bg2, ConstraintLayout cl, int v1, int v2, ImageView cross, TextView txt) {
        setConstrainLayoutTextView(bg1, bg2, cl);
        setCrossTextView(v1, v2, cross);
        if (txtBefore != null)
            setTextBefore(txtBefore.getText().toString());
        txtNow = txt;
        txtBefore = txtNow;
        setControllerInputData(txt.getId());
    }

}