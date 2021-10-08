package com.algokelvin.spinnerlist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<DataSpinner> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data.add(new DataSpinner(1, R.drawable.ic_rectangle, "Rectangle"));
        data.add(new DataSpinner(2, R.drawable.ic_ellipse, "Circle"));
        data.add(new DataSpinner(3, R.drawable.ic_polygon, "Triangle"));

        Spinner spinner = findViewById(R.id.spinner_list);

        spinner.setOnItemSelectedListener(this);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), data);
        spinner.setAdapter(spinnerAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "You choose " + data.get(position).getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}