package com.algokelvin.butterknife;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txtName)
    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // No Using Butter Knife
        TextView txtNoButterKnife = findViewById(R.id.txtNoButterKnife);
        Button btnNoButterKnife = findViewById(R.id.btnNoButterKnife);

        btnNoButterKnife.setOnClickListener(v -> txtNoButterKnife.setText("This text no using Butter Knife"));

    }

    @OnClick(R.id.btnOK)
    private void getName() {
        txtName.setText("Algokelvin");
    }
}