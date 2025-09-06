package com.algokelvin.actioneveryminute;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

abstract public class BindingActivity<T> extends AppCompatActivity {
    abstract protected void mainUI();
    abstract protected void contentView();

    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView();
        mainUI();
    }
}
