package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextLongBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayoutTextLongViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutTextLongBinding binding;

    public ItemLayoutTextLongViewHolder(@NonNull ItemLayoutTextLongBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout, int position) {
        binding.question.setText((position + 1)+ ". Question for Text Long");
        binding.edtAnswer.setLines(2);
    }
}
