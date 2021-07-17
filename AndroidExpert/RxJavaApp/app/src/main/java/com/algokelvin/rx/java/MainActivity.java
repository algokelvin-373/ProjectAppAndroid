package com.algokelvin.rx.java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.algokelvin.rx.java.RxJavaMethod01.create;
import static com.algokelvin.rx.java.RxJavaMethod01.getRxOperatorsText;
import static com.algokelvin.rx.java.RxJavaMethod01.getRxOperatorsTitle;
import static com.algokelvin.rx.java.RxOperatorStringBuffer.deleteStringBufferRxJava;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtRxJava, titleRxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiDefine();
    }

    private void uiDefine() {
        txtRxJava = findViewById(R.id.txtRxJava);
        titleRxJava = findViewById(R.id.titleRxJava);

        Button btnRxJava01 = findViewById(R.id.btnRxJava01);

        btnRxJava01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRxJava01) {
            create();
        }
        titleRxJava.setText(getRxOperatorsTitle());
        txtRxJava.setText(getRxOperatorsText());
        deleteStringBufferRxJava();
    }

}