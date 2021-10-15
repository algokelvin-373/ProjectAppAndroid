package com.algokelvin.register.data.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.model.OnDataPass;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

public class PageThreeFragment extends RegisterController {
    private OnDataPass onDataPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_three, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onDataPass = (OnDataPass) context;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] text = {"Pekerjaan", "Hobby", "Tempat Favorit Anda"};
        String[] edtHint = {"Pekerjaan", "Hobby", "Tempat Favorit Anda"};
        setSizes(text.length);
        setUIRegister(view, text, edtHint);
        setBtnNext(R.id.btn_done);
        setBtnBefore(R.id.btn_back);

        getBtnBefore().setOnClickListener(v -> {
            getOnViewPager().onSetPage(1);
        });
        getBtnNext().setOnClickListener(v -> {
            onDataPass.btnSendData(
                    getEditTexts(0).getText().toString(),
                    getEditTexts(1).getText().toString(),
                    getEditTexts(2).getText().toString()
            );
        });

    }

}