package com.algokelvin.dynamicpathdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ContextThemeWrapper wrapper;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgPath1 = findViewById(R.id.img_path_1);
        ImageView imgPath2 = findViewById(R.id.img_path_2);
        ImageView imgPath3 = findViewById(R.id.img_path_3);

        wrapper = new ContextThemeWrapper(this, R.style.DefaultScene1);
        drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_rounded, wrapper.getTheme());
        imgPath1.setImageDrawable(drawable);

        wrapper = new ContextThemeWrapper(this, R.style.DefaultScene2);
        drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_rounded, wrapper.getTheme());
        imgPath2.setImageDrawable(drawable);

        wrapper = new ContextThemeWrapper(this, R.style.DefaultScene3);
        drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_rounded, wrapper.getTheme());
        imgPath3.setImageDrawable(drawable);
    }
}