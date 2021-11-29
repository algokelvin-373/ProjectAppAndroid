package com.algokelvin.spinnerlist.utils.data;

import com.algokelvin.spinnerlist.R;
import com.algokelvin.spinnerlist.SampleData;

import java.util.ArrayList;

public class DataSample {
    private final ArrayList<SampleData> data = new ArrayList<>();

    public ArrayList<SampleData> getData() {
        data.add(new SampleData(1, R.drawable.ic_rectangle, "Rectangle"));
        data.add(new SampleData(2, R.drawable.ic_ellipse, "Circle"));
        data.add(new SampleData(3, R.drawable.ic_polygon, "Triangle"));
        return data;
    }
}
