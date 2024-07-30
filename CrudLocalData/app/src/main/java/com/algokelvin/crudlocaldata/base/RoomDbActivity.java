package com.algokelvin.crudlocaldata.base;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.algokelvin.crudlocaldata.R;
import com.algokelvin.crudlocaldata.databinding.ActivityRoomDbBinding;

public class RoomDbActivity extends AppCompatActivity {
    private ActivityRoomDbBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}