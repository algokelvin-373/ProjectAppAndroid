package algokelvin.android08.intentimplisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClick = findViewById(R.id.btn_click);

        // Intent Implicit - Go To Settings Network
        btnClick.setOnClickListener(v -> {
            Intent intentToSetNetwork = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intentToSetNetwork);
        });

    }
}