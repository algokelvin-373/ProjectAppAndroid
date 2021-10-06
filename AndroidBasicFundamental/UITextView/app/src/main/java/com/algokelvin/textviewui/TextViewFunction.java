package com.algokelvin.textviewui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

public class TextViewFunction {
    private TextView textView;
    private GradientDrawable border;
    private final Context context;

    public TextViewFunction(Context context) {
        this.context = context;
    }

    public TextViewFunction(Context context, TextView textView) {
        this.textView = textView;
        this.context = context;
    }

    public GradientDrawable getBorder() {
        return border;
    }

    public void setBackgroundOne(int color, int dp) {
        border = new GradientDrawable();
        border.setColor(color);
        border.setCornerRadius((float) (getDp(dp)));
    }

    public void setTextStyle(String txt, float size, int color) {
        textView.setText(txt);
        textView.setTextSize(size);
        textView.setTextColor(color);
    }

    private int getDp(int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }
}