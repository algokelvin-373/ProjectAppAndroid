package com.algokelvin.keyboard.simulator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainJavaActivity extends KeyboardController {
    private ConstraintLayout clFirstName, clSecondName;
    private ImageView crossFirstName, crossSecondName;
    private String txtOne = "", txtTwo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clFirstName = findViewById(R.id.cl_first_name);
        clSecondName = findViewById(R.id.cl_second_name);
        crossFirstName = findViewById(R.id.cross_first_name);
        crossSecondName = findViewById(R.id.cross_second_name);

        setControllerInputData(R.id.first_name);
        setImgCrossInput(R.id.cross_first_name);
        setControllerKeyboard();
        setKeyboardOnClickListener();

        clFirstName.setOnClickListener (v -> {
            txtTwo = txtInputData.getText().toString();
            setActionTextKeyboardSimulator(
                    R.drawable.bg_menu_white,
                    R.drawable.bg_menu_grey,
                    View.GONE, View.VISIBLE,
                    R.id.first_name,
                    R.id.cross_first_name
            );
            setTextBefore(txtOne);
        });

        clSecondName.setOnClickListener (v -> {
            txtOne = txtInputData.getText().toString();
            setActionTextKeyboardSimulator(
                    R.drawable.bg_menu_grey,
                    R.drawable.bg_menu_white,
                    View.VISIBLE, View.GONE,
                    R.id.second_name,
                    R.id.cross_second_name
            );
            setTextBefore(txtTwo);
        });

    }

    private void setActionTextKeyboardSimulator(int bg1, int bg2, int v1, int v2, int txt, int cross) {
        clSecondName.setBackgroundResource(bg1);
        clFirstName.setBackgroundResource(bg2);
        crossSecondName.setVisibility(v1);
        crossFirstName.setVisibility(v2);
        setControllerInputData(txt);
        setImgCrossInput(cross);
    }

}