package com.algokelvin.spinnerlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.spinnerlist.utils.controller.SpinnerFunction;
import com.algokelvin.spinnerlist.utils.data.DataSample;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataSample dataSample = new DataSample();
        SpinnerFunction spinnerFunction = new SpinnerFunction(this, dataSample.getData(), findViewById(R.id.spinner_list));
        spinnerFunction.setSpinner();

    }

}