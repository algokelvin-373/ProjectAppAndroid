package com.algokelvin.register.data.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.algokelvin.register.data.R;

import static com.algokelvin.register.data.model.DataRegister.*;

public class DataRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_register);

        TextView txtDataRegister = findViewById(R.id.txt_data_register);
        txtDataRegister.setText(
                getFullName() + "\n" +
                getNickName() + "\n" +
                getPlaceBirth()
        );
    }
}