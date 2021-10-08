package com.algokelvin.spinnerlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.spinnerlist.utils.controller.SpinnerFunction;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<SampleData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data.add(new SampleData(1, R.drawable.ic_rectangle, "Rectangle"));
        data.add(new SampleData(2, R.drawable.ic_ellipse, "Circle"));
        data.add(new SampleData(3, R.drawable.ic_polygon, "Triangle"));
        SpinnerFunction spinnerFunction = new SpinnerFunction(this, data, findViewById(R.id.spinner_list));
        spinnerFunction.setSpinner();

    }

}