package com.algokelvin.gson;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnParseJson = findViewById(R.id.btnParseJson);
        TextView strToJson = findViewById(R.id.strToJson);
        TextView strFromJson = findViewById(R.id.strFromJson);

        Gson gson = new Gson();

        btnParseJson.setOnClickListener(v -> {
            People People1 = new People("Zia","30","info@theappsfirm.com");
            String json = gson.toJson(People1);
            strToJson.setText("Json String: \n"+json);

            String jsonString = "{\"mAge\":\"30\",\"mEmail\":\"info@theappsfirm.com\",\"mFirstName\":\"Zia\"}";
            People People2 = gson.fromJson(jsonString, People.class);
            strFromJson.setText(" People: "+People2.mFirstName+" Age "+People2.mAge+" Email "+People2.mEmail);
        });

    }
}