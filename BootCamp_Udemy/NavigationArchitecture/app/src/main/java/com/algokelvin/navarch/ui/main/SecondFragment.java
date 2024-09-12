package com.algokelvin.navarch.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.algokelvin.navarch.R;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SecondFragmentArgs secondFragmentArgs = SecondFragmentArgs.fromBundle(getArguments());
        String yourWord = secondFragmentArgs.getYourWord();

        TextView txtYourWord = view.findViewById(R.id.data_word);
        txtYourWord.setText(yourWord);
    }
}