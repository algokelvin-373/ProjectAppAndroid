package com.algokelvin.register.data.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.register.data.R;

public class DataRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_register);

        String[] dataRegister = getIntent().getStringArrayExtra("message");
        TextView txtDataRegister = findViewById(R.id.txt_data_register);
        txtDataRegister.setText(
                dataRegister[0] + "\n" +
                        dataRegister[1] + "\n" +
                        dataRegister[2] + "\n" +
                        dataRegister[3] + "\n" +
                        dataRegister[4] + "\n" +
                        dataRegister[5] + "\n" +
                        dataRegister[6] + "\n" +
                        dataRegister[7] + "\n" +
                        dataRegister[8]
        );
    }
}