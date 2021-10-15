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

import static com.algokelvin.register.data.model.DataRegister.*;

public class PageOneFragment extends RegisterController {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] text = {"Nama Lengkap", "Nama Panggilan", "Tempat Lahir"};
        String[] edtHint = {getFullName(), getNickName(), getPlaceBirth()};
        setSizes(text.length);
        setUIRegister(view, text, edtHint);
        setBtnNext(R.id.btn_next);

        getBtnNext().setOnClickListener(v -> {
            setFullName(getEditTexts(0).getText().toString());
            setNickName(getEditTexts(1).getText().toString());
            setPlaceBirth(getEditTexts(2).getText().toString());
            getOnViewPager().onSetPage(1);
        });

    }

}