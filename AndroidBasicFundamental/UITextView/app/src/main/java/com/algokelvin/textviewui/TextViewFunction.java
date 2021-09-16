package com.algokelvin.textviewui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

public class TextViewFunction {
    private GradientDrawable border;
    private Context context;

    public TextViewFunction(Context context) {
        this.context = context;
    }

    public GradientDrawable getBorder() {
        return border;
    }

    public void setBackgroundOne(int color, int dp) {
        border = new GradientDrawable();
        border.setColor(context.getResources().getColor(R.color.sample_color_2));
        border.setCornerRadius((float) (getDp(dp)));
    }

    private int getDp(int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }
}
