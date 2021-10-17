package com.algokelvin.spinnerlist.utils.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.algokelvin.spinnerlist.R;
import com.algokelvin.spinnerlist.SampleData;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    private LayoutInflater inflter;
    private ArrayList<SampleData> sampleData;

    public SpinnerAdapter(Context applicationContext, ArrayList<SampleData> sampleData) {
        this.inflter = (LayoutInflater.from(applicationContext));
        this.sampleData = sampleData;
    }

    @Override
    public int getCount() {
        return sampleData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_item, null);

        ImageView icon = (ImageView) convertView.findViewById(R.id.img_spinner);
        TextView names = (TextView) convertView.findViewById(R.id.txt_spinner_item);
        icon.setImageResource(sampleData.get(position).getImage());
        names.setText(sampleData.get(position).getName());

        return convertView;
    }
}
