package com.algokelvin.recyclerviewcustomitems.adapter.viewholder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.algokelvin.recyclerviewcustomitems.adapter.CustomItemAdapter;
import com.algokelvin.recyclerviewcustomitems.databinding.ItemLayoutTextWithButtonBinding;
import com.algokelvin.recyclerviewcustomitems.model.ItemLayout;

public class ItemLayoutTextWithButtonViewHolder extends RecyclerView.ViewHolder {
    private final ItemLayoutTextWithButtonBinding binding;
    private final Context context;

    public ItemLayoutTextWithButtonViewHolder(@NonNull ItemLayoutTextWithButtonBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    public void bind(ItemLayout itemLayout, int position) {
        binding.question.setText((position + 1)+ ". Question with Button");

        //binding.edtAnswer.requestFocus();
        /*CustomItemAdapter.editTextViewNow = binding.edtAnswer;
        binding.edtAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(binding.edtAnswer, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        binding.edtAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(binding.edtAnswer, InputMethodManager.SHOW_IMPLICIT);
            }
        });*/
        binding.edtAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tempChange = editable.toString();
            }
        });

        binding.btnAction.setOnClickListener(v -> {
            binding.edtAnswer.setText("MyAnswer");
        });
    }
}
