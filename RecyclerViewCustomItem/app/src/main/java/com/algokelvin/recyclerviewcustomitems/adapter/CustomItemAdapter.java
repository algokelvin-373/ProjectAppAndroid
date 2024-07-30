package com.algokelvin.recyclerviewcustomitems.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayout4ViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayoutTextLongViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayoutTextNumberHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayoutTextViewHolder;
import com.algokelvin.recyclerviewcustomitems.adapter.viewholder.ItemLayoutTextWithButtonViewHolder;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextLongBinding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextNumberBinding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextShortBinding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextWithButtonBinding;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType4Binding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

import java.util.ArrayList;
import java.util.Objects;

public class CustomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Objects> bindingLayout = new ArrayList<Objects>();

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private final ArrayList<ItemLayout> itemLayouts;
    private int currentPosition = 1;

    public CustomItemAdapter(ArrayList<ItemLayout> itemLayouts) {
        this.itemLayouts = itemLayouts;
    }

    @Override
    public int getItemViewType(int position) {
        return itemLayouts.get(position).getIsType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            ItemLayoutTextShortBinding binding = ItemLayoutTextShortBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayoutTextViewHolder(binding);
        } else if (viewType == TYPE_TWO){
            ItemLayoutTextLongBinding binding = ItemLayoutTextLongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayoutTextLongViewHolder(binding);
        } else if (viewType == TYPE_THREE){
            ItemLayoutTextWithButtonBinding binding = ItemLayoutTextWithButtonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayoutTextWithButtonViewHolder(binding);
        } else if (viewType == TYPE_FOUR){
            ItemLayoutTextNumberBinding binding = ItemLayoutTextNumberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemLayoutTextNumberHolder(binding);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemLayout item = itemLayouts.get(position);
        if (holder instanceof ItemLayoutTextViewHolder) {
            ((ItemLayoutTextViewHolder) holder).bind(item, position);
        } else if (holder instanceof ItemLayoutTextLongViewHolder) {
            ((ItemLayoutTextLongViewHolder) holder).bind(item, position);
        } else if (holder instanceof ItemLayoutTextWithButtonViewHolder) {
            ((ItemLayoutTextWithButtonViewHolder) holder).bind(item, position);
        } else if (holder instanceof ItemLayoutTextNumberHolder) {
            ((ItemLayoutTextNumberHolder) holder).bind(item, position);
        }
    }

    @Override
    public int getItemCount() {
        return currentPosition;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void nextItem() {
        if (currentPosition < itemLayouts.size() - 1) {
            currentPosition++;
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetPosition() {
        currentPosition = 0;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void goToLast() {
        currentPosition = itemLayouts.size() - 1;
        notifyDataSetChanged();
    }
}
