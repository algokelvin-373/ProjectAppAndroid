package com.algokelvin.insertdb.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.insertdb.R;

public class SqliteDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_db);

    }
}