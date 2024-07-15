package com.algokelvin.recyclerviewcustomitems.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout1ViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout2ViewHolder;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType1Binding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType2Binding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

import java.util.ArrayList;

public class CustomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private final ArrayList<ItemLayout> itemLayouts;

    public CustomItemAdapter(ArrayList<ItemLayout> itemLayouts) {
        this.itemLayouts = itemLayouts;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemLayouts.get(position).getIsType().equals("1")) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            ItemLayoutType1Binding binding = ItemLayoutType1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout1ViewHolder(binding);
        } else {
            ItemLayoutType2Binding binding = ItemLayoutType2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout2ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemLayout item = itemLayouts.get(position);
        if (holder instanceof ItemLayout1ViewHolder) {
            ((ItemLayout1ViewHolder) holder).bind(item);
        } else if (holder instanceof ItemLayout2ViewHolder) {
            ((ItemLayout2ViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemLayouts.size();
    }
}
