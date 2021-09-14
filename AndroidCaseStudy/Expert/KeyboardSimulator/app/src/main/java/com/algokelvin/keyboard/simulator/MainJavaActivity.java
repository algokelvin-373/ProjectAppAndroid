package com.algokelvin.keyboard.simulator;

import android.os.Bundle;

public class MainJavaActivity extends KeyboardController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClData(R.id.cl_first_name, R.id.cl_second_name, R.id.cl_third_name);
        setCrossData(R.id.cross_first_name, R.id.cross_second_name, R.id.cross_third_name);
        setTxtData(R.id.first_name, R.id.second_name, R.id.third_name);
        setKeyboardController(R.id.first_name, R.id.cross_first_name);

        getClData(0).setOnClickListener (v -> setTextAction(0));
        getClData(1).setOnClickListener (v -> setTextAction(1));
        getClData(2).setOnClickListener(v -> setTextAction(2));

    }

}