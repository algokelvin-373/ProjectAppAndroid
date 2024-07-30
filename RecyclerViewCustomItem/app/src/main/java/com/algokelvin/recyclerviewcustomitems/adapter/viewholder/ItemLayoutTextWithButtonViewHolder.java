package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextWithButtonBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayoutTextWithButtonViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutTextWithButtonBinding binding;

    public ItemLayoutTextWithButtonViewHolder(@NonNull ItemLayoutTextWithButtonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ItemLayout itemLayout, int position) {
        binding.question.setText((position + 1)+ ". Question with Button");
        binding.btnAction.setOnClickListener(v -> {
            binding.edtAnswer.setText("MyAnswer");
        });
    }
}
