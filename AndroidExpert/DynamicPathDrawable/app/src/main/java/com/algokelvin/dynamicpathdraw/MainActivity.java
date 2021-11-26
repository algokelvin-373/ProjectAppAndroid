package com.algokelvin.dynamicpathdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ContextThemeWrapper wrapper;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*int[] attrs = {R.attr.fill_color_path, R.attr.string_text};
        TypedArray typedArray = obtainStyledAttributes(R.style.DefaultScene1, attrs);*/

        int[] style = {R.style.DefaultScene1, R.style.DefaultScene2, R.style.DefaultScene3};

        @SuppressLint("CutPasteId")
        ImageView[] imgPath = {findViewById(R.id.img_path_1), findViewById(R.id.img_path_2), findViewById(R.id.img_path_3)};

        for (int x = 0; x < style.length; x++) {
            drawable = pathDrawableController(R.drawable.ic_rounded, style[x]);
            imgPath[x].setImageDrawable(drawable);
        }

    }

    private Drawable pathDrawableController(int pathDraw, int style) {
        wrapper = new ContextThemeWrapper(this, style);
        return VectorDrawableCompat.create(getResources(), pathDraw, wrapper.getTheme());
    }
}