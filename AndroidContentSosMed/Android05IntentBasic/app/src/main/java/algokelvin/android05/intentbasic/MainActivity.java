package algokelvin.android05.intentbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClick = findViewById(R.id.btn_click);
        btnClick.setOnClickListener(v -> {
            Intent intentToPage = new Intent(this, PageActivity.class);
            startActivity(intentToPage);
        });

    }
}