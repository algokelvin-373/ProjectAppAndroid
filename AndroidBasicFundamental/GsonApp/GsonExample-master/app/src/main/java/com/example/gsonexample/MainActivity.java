package com.example.gsonexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView stringData1 = findViewById(R.id.stringData1);
                TextView stringData2 = findViewById(R.id.stringData2);

                Gson gson = new Gson();

                Employee employee1 = new Employee("Zia","30","info@theappsfirm.com");
                String json = gson.toJson(employee1);
                stringData1.setText("Json String: \n"+json);

                String jsonString = "{\"mAge\":\"30\",\"mEmail\":\"info@theappsfirm.com\",\"mFirstName\":\"Zia\"}";
                Employee employee2 = gson.fromJson(jsonString, Employee.class);
                stringData2.setText(" Employee: "+employee2.mFirstName+" Age "+employee2.mAge+" Email "+employee2.mEmail);

            }
        });



    }
}
