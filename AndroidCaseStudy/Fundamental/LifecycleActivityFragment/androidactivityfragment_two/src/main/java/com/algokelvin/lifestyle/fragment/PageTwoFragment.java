package com.algokelvin.lifestyle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.algokelvin.lifestyle.R;
import com.algokelvin.lifestyle.utils.OnViewPager;

import org.jetbrains.annotations.NotNull;

public class PageTwoFragment extends Fragment {
    private String data1;
    private OnViewPager onViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_two, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onViewPager = (OnViewPager) context;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnNext = view.findViewById(R.id.btn_next);
        Button btnBefore = view.findViewById(R.id.btn_back);
        btnNext.setOnClickListener(v -> {
            onViewPager.onSetPage(2);
        });
        btnBefore.setOnClickListener(v -> {
            onViewPager.onSetPage(0);
        });


    }

}