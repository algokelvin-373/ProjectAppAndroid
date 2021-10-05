package com.algokelvin.register.data.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

import static com.algokelvin.register.data.model.DataRegister.*;

public class PageTwoFragment extends RegisterController {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] idLayout = {R.id.layout_data_1, R.id.layout_data_2, R.id.layout_data_3};
        String[] text = {"Alamat Rumah", "Kota", "No. HP"};
        String[] edtHint = {getAddress(), getCity(), getNoHP()};
        setSizes(idLayout.length);
        setUIRegister(view, idLayout, text, edtHint);

        Button btnNext = view.findViewById(R.id.btn_next);
        Button btnBefore = view.findViewById(R.id.btn_back);
        btnNext.setOnClickListener(v -> {
            setAddress(getEditTexts(0).getText().toString());
            setCity(getEditTexts(1).getText().toString());
            setNoHP(getEditTexts(2).getText().toString());
            getOnViewPager().onSetPage(2);
        });
        btnBefore.setOnClickListener(v -> {
            getOnViewPager().onSetPage(0);
        });

    }

}