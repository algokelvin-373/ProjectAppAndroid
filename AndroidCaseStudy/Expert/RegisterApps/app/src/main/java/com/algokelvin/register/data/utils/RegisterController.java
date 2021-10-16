package com.algokelvin.register.data.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.algokelvin.register.data.R;

import org.jetbrains.annotations.NotNull;

public class RegisterController extends Fragment {
    private OnViewPager onViewPager;
    private OnDataPass onDataPass;
    private View viewLayout;
    private TextView[] textViews;
    private EditText[] editTexts;
    private Button btnNext, btnBefore;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        onViewPager = (OnViewPager) context;
        onDataPass = (OnDataPass) context;
    }

    public void setSizes(int sizes) {
        textViews = new TextView[sizes];
        editTexts = new EditText[sizes];
    }

    public void setUIRegister(View viewLayout, String[] txt, String[] edtHint) {
        this.viewLayout = viewLayout;
        setUIInputData(txt, edtHint);
    }

    public Button getBtnNext() {
        return btnNext;
    }

    public void setBtnNext(int btnNext) {
        this.btnNext = viewLayout.findViewById(btnNext);
    }

    public Button getBtnBefore() {
        return btnBefore;
    }

    public void setBtnBefore(int btnBefore) {
        this.btnBefore = viewLayout.findViewById(btnBefore);
    }

    public EditText getEditTexts(int x) {
        return editTexts[x];
    }

    public OnViewPager getOnViewPager() {
        return onViewPager;
    }

    public OnDataPass getOnDataPass() {
        return onDataPass;
    }

    private void setUIInputData(String[] txt, String[] edtHint) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, setDp(10), 0, 0);
        LinearLayout clRegister = viewLayout.findViewById(R.id.cl_register_1);

        for (int x = 0; x < txt.length; x++) {
            View v = View.inflate(getActivity(), R.layout.include_item_data_register, null);
            textViews[x] = v.findViewById(R.id.title_input_data);
            editTexts[x] = v.findViewById(R.id.input_data);
            textViews[x].setText(txt[x]);
            editTexts[x].setHint(edtHint[x]);
            clRegister.addView(v);
        }
    }

    private int setDp(int x) {
        Resources r = getContext().getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, x, r.getDisplayMetrics());
    }
}
