package com.algokelvin.lifecycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.algokelvin.lifecycle.utils.OnDataPass;
import com.algokelvin.lifecycle.R;

import org.jetbrains.annotations.NotNull;

public class FourFragment extends Fragment {
    private OnDataPass onDataPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onAttach(@NonNull @org.jetbrains.annotations.NotNull Context context) {
        super.onAttach(context);
        onDataPass = (OnDataPass) context;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtFragment = view.findViewById(R.id.txt_fragment_four);
        passData(txtFragment.getText().toString());

    }

    private void passData(String data) {
        onDataPass.onDataPass(data);
    }
}