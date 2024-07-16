package com.algokelvin.recyclerviewcustomitems.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout1ViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout2ViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout3ViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout4ViewHolder;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType1Binding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType2Binding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType3Binding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType4Binding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

import java.util.ArrayList;

public class CustomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private final ArrayList<ItemLayout> itemLayouts;

    public CustomItemAdapter(ArrayList<ItemLayout> itemLayouts) {
        this.itemLayouts = itemLayouts;
    }

    @Override
    public int getItemViewType(int position) {
        switch (itemLayouts.get(position).getIsType()) {
            case "1":
                return TYPE_ONE;
            case "2":
                return TYPE_TWO;
            case "3":
                return TYPE_THREE;
            default:
                return TYPE_FOUR;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            ItemLayoutType1Binding binding = ItemLayoutType1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout1ViewHolder(binding);
        } else if (viewType == TYPE_TWO){
            ItemLayoutType2Binding binding = ItemLayoutType2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout2ViewHolder(binding);
        } else if (viewType == TYPE_THREE){
            ItemLayoutType3Binding binding = ItemLayoutType3Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout3ViewHolder(binding);
        } else if (viewType == TYPE_FOUR){
            ItemLayoutType4Binding binding = ItemLayoutType4Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayout4ViewHolder(binding);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemLayout item = itemLayouts.get(position);
        if (holder instanceof ItemLayout1ViewHolder) {
            ((ItemLayout1ViewHolder) holder).bind(item);
        } else if (holder instanceof ItemLayout2ViewHolder) {
            ((ItemLayout2ViewHolder) holder).bind(item);
        } else if (holder instanceof ItemLayout3ViewHolder) {
            ((ItemLayout3ViewHolder) holder).bind(item);
        } else if (holder instanceof ItemLayout4ViewHolder) {
            ((ItemLayout4ViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return itemLayouts.size();
    }
}
