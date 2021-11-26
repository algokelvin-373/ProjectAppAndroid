package com.algokelvin.dynamicpathdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgPath1 = findViewById(R.id.img_path_1);

        ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.DefaultScene1);
        Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_rounded, wrapper.getTheme());
        imgPath1.setImageDrawable(drawable);
    }
}