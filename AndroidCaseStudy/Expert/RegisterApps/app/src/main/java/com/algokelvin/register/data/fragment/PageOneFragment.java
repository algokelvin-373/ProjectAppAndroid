package com.algokelvin.register.data.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.model.OnDataPass;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

import static com.algokelvin.register.data.model.DataRegister.*;

public class PageOneFragment extends RegisterController {
    private OnDataPass onDataPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String message = bundle.getString("message");
        Log.i("message_factivity", message);
        return inflater.inflate(R.layout.fragment_page_one, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onDataPass = (OnDataPass) context;
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
            onDataPass.btnSendData("This is my data from fragment one"); // Using interface to send fragment two
            setFullName(getEditTexts(0).getText().toString());
            setNickName(getEditTexts(1).getText().toString());
            setPlaceBirth(getEditTexts(2).getText().toString());
            getOnViewPager().onSetPage(1);
        });

    }

}