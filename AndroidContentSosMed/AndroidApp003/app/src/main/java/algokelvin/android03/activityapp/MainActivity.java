package algokelvin.android03.activityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtMsg = findViewById(R.id.txt_msg);
        TextView txtClick = findViewById(R.id.txt_click);

        txtMsg.setText("Hello, AlgoKelvin"); // given text

        txtClick.setOnClickListener(view -> txtMsg.setText("Welcome"));

    }
}