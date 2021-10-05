package com.algokelvin.register.data.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.algokelvin.register.data.R;

import org.jetbrains.annotations.NotNull;

public class RegisterController extends Fragment {
    private OnViewPager onViewPager;
    private View[] view;
    private TextView[] textViews;
    private EditText[] editTexts;
    private int sizes;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onViewPager = (OnViewPager) context;
    }

    public void setSizes(int sizes) {
        this.sizes = sizes;
        view = new View[sizes];
        textViews = new TextView[sizes];
        editTexts = new EditText[sizes];
    }

    public void setUIRegister(View viewLayout, int[] layoutId, String[] txt, String[] edtHint) {
        for (int x = 0; x < sizes; x++) {
            view[x] = viewLayout.findViewById(layoutId[x]);
            textViews[x] = view[x].findViewById(R.id.title_input_data);
            editTexts[x] = view[x].findViewById(R.id.input_data);

            textViews[x].setText(txt[x]);
            editTexts[x].setHint(edtHint[x]);
        }
    }

    public EditText getEditTexts(int x) {
        return editTexts[x];
    }

    public OnViewPager getOnViewPager() {
        return onViewPager;
    }
}
