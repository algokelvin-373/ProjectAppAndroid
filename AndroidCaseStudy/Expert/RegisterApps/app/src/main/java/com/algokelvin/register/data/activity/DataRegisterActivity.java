package com.algokelvin.register.data.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.register.data.R;
import com.algokelvin.register.data.model.DataRegister;

public class DataRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_register);

        DataRegister dataRegister = getIntent().getParcelableExtra("message");
        TextView txtDataRegister = findViewById(R.id.txt_data_register);
        txtDataRegister.setText(
                dataRegister.getFullName() + "\n" +
                        dataRegister.getNickName() + "\n" +
                        dataRegister.getPlaceBirth() + "\n" +
                        dataRegister.getAddress() + "\n" +
                        dataRegister.getCity() + "\n" +
                        dataRegister.getNoHP() + "\n" +
                        dataRegister.getJob() + "\n" +
                        dataRegister.getHobby() + "\n" +
                        dataRegister.getFavoritePlace()
        );
    }
}