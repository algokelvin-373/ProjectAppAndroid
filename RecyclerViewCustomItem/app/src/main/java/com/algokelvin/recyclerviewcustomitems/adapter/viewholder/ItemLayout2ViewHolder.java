package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutType2Binding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayout2ViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutType2Binding binding;

    public ItemLayout2ViewHolder(@NonNull ItemLayoutType2Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout) {
        binding.textViewType2.setText("Text Item Layout 2");
    }
}
