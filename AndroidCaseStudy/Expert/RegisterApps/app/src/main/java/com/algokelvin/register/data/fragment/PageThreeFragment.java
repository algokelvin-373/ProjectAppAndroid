package com.algokelvin.register.data.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.activity.DataRegisterActivity;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

import static com.algokelvin.register.data.model.DataRegister.*;

public class PageThreeFragment extends RegisterController {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] idLayout = {R.id.layout_data_1, R.id.layout_data_2, R.id.layout_data_3};
        String[] text = {"Pekerjaan", "Hobby", "Tempat Favorit Anda"};
        String[] edtHint = {getJob(), getHobby(), getFavoritePlace()};
        setSizes(idLayout.length);
        setUIRegister(view, idLayout, text, edtHint);

        Button btnBefore = view.findViewById(R.id.btn_back);
        Button btnDone = view.findViewById(R.id.btn_done);
        btnBefore.setOnClickListener(v -> {
            getOnViewPager().onSetPage(1);
        });
        btnDone.setOnClickListener(v -> {
            setJob(getEditTexts(0).getText().toString());
            setHobby(getEditTexts(1).getText().toString());
            setFavoritePlace(getEditTexts(2).getText().toString());
            Intent intentDetails = new Intent(getActivity(), DataRegisterActivity.class);
            startActivity(intentDetails);
        });

    }

}