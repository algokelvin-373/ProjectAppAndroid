package com.algokelvin.recyclerviewcustomitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.algokelvin.recyclerviewcustomitems.adapter.CustomItemAdapter;
import com.algokelvin.recyclerviewcustomitems.databinding.ActivityMainBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize(binding);
    }

    private void initialize(ActivityMainBinding binding) {
        ArrayList<ItemLayout> itemLayouts = new ArrayList<>();
        itemLayouts.add(new ItemLayout("1"));
        itemLayouts.add(new ItemLayout("2"));

        CustomItemAdapter adapter = new CustomItemAdapter(itemLayouts);
        binding.rvCustomItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCustomItems.setAdapter(adapter);
    }

}