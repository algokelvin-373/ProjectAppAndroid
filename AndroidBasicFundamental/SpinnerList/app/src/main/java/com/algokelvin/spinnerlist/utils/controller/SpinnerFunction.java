package com.algokelvin.spinnerlist.utils.controller;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.algokelvin.spinnerlist.SampleData;

import java.util.ArrayList;

public class SpinnerFunction implements AdapterView.OnItemSelectedListener {
    private final ArrayList<SampleData> data;
    private final Spinner spinner;
    private final Context context;

    public SpinnerFunction(Context context, ArrayList<SampleData> data, Spinner spinner) {
        this.context = context;
        this.data = data;
        this.spinner = spinner;
    }

    public void setSpinner() {
        spinner.setOnItemSelectedListener(this);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context, data);
        spinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "You choose " + data.get(position).getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
