package com.algokelvin.crudlocaldata.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.algokelvin.crudlocaldata.adapter.DbDataAdapter;
import com.algokelvin.crudlocaldata.base.function.RoomDbFunction;
import com.algokelvin.crudlocaldata.databinding.ActivityRoomDbBinding;
import com.algokelvin.crudlocaldata.db.task.UserTask;

public class RoomDbActivity extends AppCompatActivity {
    private RoomDbFunction function;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRoomDbBinding binding = ActivityRoomDbBinding.inflate(getLayoutInflater());
        UserTask task = new UserTask(this);
        activity = this;
        function = new RoomDbFunction(task);
        setContentView(binding.getRoot());
        initialize(binding);
    }

    private void initialize(ActivityRoomDbBinding binding) {
        binding.btnInsertRoom.setOnClickListener(v -> {
            function.insertToRoom(message -> {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            });
        });

        function.getAllData(data -> {
            DbDataAdapter adapter = new DbDataAdapter(data);
            binding.rvItemDb.setLayoutManager(new LinearLayoutManager(this));
            binding.rvItemDb.setAdapter(adapter);
        });

    }

}