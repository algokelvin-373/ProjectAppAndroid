package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextNumberBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayoutTextNumberHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutTextNumberBinding binding;

    public ItemLayoutTextNumberHolder(@NonNull ItemLayoutTextNumberBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout, int position) {
        binding.question.setText((position + 1)+ ". Question for Text Number");
    }
}
