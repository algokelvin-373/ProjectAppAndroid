package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextShortBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayoutTextViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutTextShortBinding binding;

    public ItemLayoutTextViewHolder(@NonNull ItemLayoutTextShortBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout, int position) {
        binding.question.setText((position + 1)+ ". Question for Text Short");
    }
}
