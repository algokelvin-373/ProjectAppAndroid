package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType1Binding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayout1ViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutType1Binding binding;

    public ItemLayout1ViewHolder(@NonNull ItemLayoutType1Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout) {
        binding.textViewType1.setText("Text Item Layout 1");
    }
}
