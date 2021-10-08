package com.algokelvin.register.data.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.utils.RegisterController;

import org.jetbrains.annotations.NotNull;

public class PageOneFragment extends RegisterController {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, setDp(10), 0, 0);

        LinearLayout clRegister1 = view.findViewById(R.id.cl_register_1);

        View v = View.inflate(getActivity(), R.layout.include_item_data_register, null);
        TextView txt1 = v.findViewById(R.id.title_input_data);
        EditText edt1 = v.findViewById(R.id.input_data);
        txt1.setText("Full Name");
        edt1.setHint("Input Full Name");
        clRegister1.addView(v);

        View v2 = View.inflate(getActivity(), R.layout.include_item_data_register, null);
        v2.setLayoutParams(params);
        TextView txt2 = v2.findViewById(R.id.title_input_data);
        EditText edt2 = v2.findViewById(R.id.input_data);
        txt2.setText("Nick Name");
        edt2.setHint("Input Nick Name");
        clRegister1.addView(v2);

        View v3 = View.inflate(getActivity(), R.layout.include_item_data_register, null);
        v3.setLayoutParams(params);
        TextView txt3 = v3.findViewById(R.id.title_input_data);
        EditText edt3 = v3.findViewById(R.id.input_data);
        txt3.setText("Place Birth");
        edt3.setHint("Input Place Birth");
        clRegister1.addView(v3);

        /*int[] idLayout = {R.id.layout_data_1, R.id.layout_data_2, R.id.layout_data_3};
        String[] text = {"Nama Lengkap", "Nama Panggilan", "Tempat Lahir"};
        String[] edtHint = {getFullName(), getNickName(), getPlaceBirth()};
        setSizes(idLayout.length);
        setUIRegister(view, idLayout, text, edtHint);

        Button btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            setFullName(getEditTexts(0).getText().toString());
            setNickName(getEditTexts(1).getText().toString());
            setPlaceBirth(getEditTexts(2).getText().toString());
            getOnViewPager().onSetPage(1);
        });*/

    }

    private int setDp(int x) {
        Resources r = getContext().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, r.getDisplayMetrics());
    }

}