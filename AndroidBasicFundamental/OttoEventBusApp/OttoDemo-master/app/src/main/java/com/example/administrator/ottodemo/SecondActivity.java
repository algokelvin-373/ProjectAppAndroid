package com.example.administrator.ottodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button bUtton = (Button) findViewById(R.id.post);
        mEditText = (EditText) findViewById(R.id.edit);
        bUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mEditText.getText().toString().trim();
                App.getMainThreadBusInstance().post(trim);
            }
        });
    }
}
