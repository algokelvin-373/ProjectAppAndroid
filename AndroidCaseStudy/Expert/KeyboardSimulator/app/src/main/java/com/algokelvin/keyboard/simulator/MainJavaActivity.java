package com.algokelvin.keyboard.simulator;

import android.os.Bundle;

public class MainJavaActivity extends KeyboardController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setKeyboardController();
        setControllerInputData(R.id.first_name);
        setImgCrossInput(R.id.cross_first_name);
        setControllerKeyboard();
        setKeyboardOnClickListener();

        getClData(0).setOnClickListener (v -> setTextAction(0));

        getClData(1).setOnClickListener (v -> setTextAction(1));

        getClData(2).setOnClickListener(v -> setTextAction(2));

    }

}