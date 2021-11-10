package com.algokelvin.register.data.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

public class PageTwoFragment extends RegisterController {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] text = {"Alamat Rumah", "Kota", "No. HP"};
        String[] edtHint = {"Alamat Rumah", "Kota", "No. HP"};
        setSizes(text.length);
        setUIRegister(view, text, edtHint);
        setBtnNext(R.id.btn_next);
        setBtnBefore(R.id.btn_back);

        btnNext.setOnClickListener(v -> {
            onDataPass.btnSendData(
                    editTexts[0].getText().toString(),
                    editTexts[1].getText().toString(),
                    editTexts[2].getText().toString()
            );
            onViewPager.onSetPage(2);
        });
        btnBefore.setOnClickListener(v -> {
            onDataPass.minusCountData(3);
            onViewPager.onSetPage(0);
        });

    }

}