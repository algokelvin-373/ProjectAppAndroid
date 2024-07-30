package com.algokelvin.crudlocaldata.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.crudlocaldata.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGoToSqlite.setOnClickListener(v -> {
            Intent intentToSqlite = new Intent(this, SqliteDbActivity.class);
            startActivity(intentToSqlite);
        });

        binding.btnGoToRoom.setOnClickListener(v -> {
            Intent intentToRoom = new Intent(this, RoomDbActivity.class);
            startActivity(intentToRoom);
        });
    }
}