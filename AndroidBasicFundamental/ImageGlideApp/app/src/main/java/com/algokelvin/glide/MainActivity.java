package com.algokelvin.glide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.glide.model.DataDummy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewImage = findViewById(R.id.recyclerView_Image);

        recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewImage.setAdapter(new DataAdapter(this, DataDummy.prepareData()));

    }
}