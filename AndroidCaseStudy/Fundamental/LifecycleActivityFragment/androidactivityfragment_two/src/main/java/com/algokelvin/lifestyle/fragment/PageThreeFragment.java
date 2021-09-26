package com.algokelvin.lifestyle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.algokelvin.lifestyle.R;

import org.jetbrains.annotations.NotNull;

public class PageThreeFragment extends Fragment {
    private String data1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView txtFragment = view.findViewById(R.id.txt_fragment_one);
//        this.data1 = txtFragment.getText().toString();
//        passData(data1);

    }

}