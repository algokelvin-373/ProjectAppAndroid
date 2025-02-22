package com.algokelvin.performancedatascroll;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView listGrid = findViewById(R.id.grid_item);

        List<KamenRider> list = DummyItems.getListData200();
        GridViewAdapter adapter = new GridViewAdapter(this, list);
        listGrid.setAdapter(adapter);
    }
}