package com.algokelvin.layermapgoogle;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.layermapgoogle.utils.SecretApplication;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY_GOOGLE_MAP = "com.google.android.geo.API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = SecretApplication.getApiKey(this, API_KEY_GOOGLE_MAP);
        if (apiKey != null) {
            Toast.makeText(getApplicationContext(), "Key: "+apiKey, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Key is null", Toast.LENGTH_LONG).show();
        }

    }
}