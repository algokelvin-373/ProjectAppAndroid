package com.algokelvin.register.data.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.utils.OnViewPager;

import org.jetbrains.annotations.NotNull;

import static com.algokelvin.register.data.model.DataRegister.*;

public class PageOneFragment extends Fragment {
    private OnViewPager onViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_one, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onViewPager = (OnViewPager) context;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View vInput1 = view.findViewById(R.id.layout_data_1);
        TextView txt1 = vInput1.findViewById(R.id.title_input_data);
        EditText edt1 = vInput1.findViewById(R.id.input_data);
        txt1.setText("Nama Lengkap");
        edt1.setHint(getFullName());

        View vInput2 = view.findViewById(R.id.layout_data_2);
        TextView txt2 = vInput2.findViewById(R.id.title_input_data);
        EditText edt2 = vInput2.findViewById(R.id.input_data);
        txt2.setText("Nama Panggilan");
        edt2.setHint(getNickName());

        View vInput3 = view.findViewById(R.id.layout_data_3);
        TextView txt3 = vInput3.findViewById(R.id.title_input_data);
        EditText edt3 = vInput3.findViewById(R.id.input_data);
        txt3.setText("Tempat Lahir");
        edt3.setHint(getPlaceBirth());

        Button btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            setFullName(edt1.getText().toString());
            setNickName(edt2.getText().toString());
            setPlaceBirth(edt3.getText().toString());
            onViewPager.onSetPage(1);
        });

    }

}