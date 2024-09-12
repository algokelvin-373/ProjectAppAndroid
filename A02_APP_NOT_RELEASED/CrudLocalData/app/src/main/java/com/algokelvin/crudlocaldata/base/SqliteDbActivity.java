package com.algokelvin.crudlocaldata.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.crudlocaldata.base.function.SqliteDbFunction;
import com.algokelvin.crudlocaldata.databinding.ActivitySqliteDbBinding;
import com.algokelvin.crudlocaldata.db.AppDbSqlite;

public class SqliteDbActivity extends AppCompatActivity {
    private ActivitySqliteDbBinding binding;
    private AppDbSqlite db;
    private Activity activity;
    private SqliteDbFunction function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySqliteDbBinding.inflate(getLayoutInflater());
        db = new AppDbSqlite(this);
        function = new SqliteDbFunction(db);
        activity = this;
        setContentView(binding.getRoot());
        initialize(binding);
    }

    private void initialize(ActivitySqliteDbBinding binding) {
        binding.btnInsertSqlite.setOnClickListener(v -> {
            function.insertToSqlite();
            Toast.makeText(activity, "Success Add Data", Toast.LENGTH_SHORT).show();
        });

        binding.btnDeleteAll.setOnClickListener(v -> {

        });
    }
}