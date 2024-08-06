package com.algokelvin.crudlocaldata.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.algokelvin.crudlocaldata.adapter.DbDataAdapter;
import com.algokelvin.crudlocaldata.base.action.RoomDbAction;
import com.algokelvin.crudlocaldata.base.function.RoomDbFunction;
import com.algokelvin.crudlocaldata.databinding.ActivityRoomDbBinding;
import com.algokelvin.crudlocaldata.db.entity.User;
import com.algokelvin.crudlocaldata.db.task.UserTask;

public class RoomDbActivity extends AppCompatActivity implements RoomDbAction {
    private ActivityRoomDbBinding binding;
    private RoomDbFunction function;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDbBinding.inflate(getLayoutInflater());
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
                getAllDataUsers();
            });
        });

        getAllDataUsers();

    }

    @Override
    public void deleteDataInDb(User user) {
        function.deleteData(user, message -> {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            getAllDataUsers();
        });
    }

    private void getAllDataUsers() {
        function.getAllData(data -> {
            DbDataAdapter adapter = new DbDataAdapter(data, this);
            binding.rvItemDb.setLayoutManager(new LinearLayoutManager(this));
            binding.rvItemDb.setAdapter(adapter);
        });
    }
}