package com.algokelvin.crudlocaldata.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.crudlocaldata.databinding.ActivitySqliteDbBinding;

public class SqliteDbActivity extends AppCompatActivity {
    private ActivitySqliteDbBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySqliteDbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}