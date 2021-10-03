package com.algokelvin.textviewui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextViewFunction textViewFunction = new TextViewFunction(this);

        // START - TextView Programmatically
        TextView txtProgrammatically = findViewById(R.id.txt_programmatically);
        txtProgrammatically.setText("TextView Programmatically");
        txtProgrammatically.setTextSize(20.0f);
        txtProgrammatically.setTextColor(getResources().getColor(R.color.black));
        txtProgrammatically.setGravity(Gravity.CENTER);
        Typeface typeface = ResourcesCompat.getFont(this ,R.font.roboto_mono_thin);
        txtProgrammatically.setTypeface(typeface);
        // END - TextView Programmatically

        // START - TextView with Border Programmatically 1
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int size16InDp = 16, size8InDp = 8;
        params1.setMargins(getDp(size16InDp), getDp(size8InDp), getDp(size16InDp), 0);

        GradientDrawable border1 = new GradientDrawable();
        border1.setColor(getResources().getColor(R.color.sample_color_1));
        border1.setCornerRadius((float) (getDp(10)));

        TextView txtBorderProgrammatically1 = findViewById(R.id.txt_border_xml_programmatically_1);
        txtBorderProgrammatically1.setBackground(border1);
        txtBorderProgrammatically1.setText(getString(R.string.textview_with_border_xml_1));
        txtBorderProgrammatically1.setTextSize(14.0f);
        txtBorderProgrammatically1.setTextColor(getResources().getColor(R.color.black));
        txtBorderProgrammatically1.setGravity(Gravity.CENTER);
        txtBorderProgrammatically1.setTypeface(Typeface.SANS_SERIF);
        txtBorderProgrammatically1.setLayoutParams(params1);
        txtBorderProgrammatically1.setPadding(0, getDp(size16InDp), 0, getDp(size16InDp));
        // END - TextView with Border Programmatically 1

        // START - TextView with Background Programmatically
        TextView txtXmlBgProgrammatically = findViewById(R.id.txt_xml_bg_programmatically);
        textViewFunction.setBackgroundOne(getResources().getColor(R.color.sample_color_2), 10);
        txtXmlBgProgrammatically.setBackground(textViewFunction.getBorder());
        // END - TextView with Background Programmatically

    }

    private int getDp(int sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }

}