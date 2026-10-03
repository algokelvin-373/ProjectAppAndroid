package com.algokelvin.deeplinktrain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DeepLink";

    private TextView tvData, tvHost, tvPath, tvCode, tvRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = findViewById(R.id.tv_data);
        tvHost = findViewById(R.id.tv_host);
        tvPath = findViewById(R.id.tv_path);
        tvCode = findViewById(R.id.tv_code);
        tvRef = findViewById(R.id.tv_ref);

        handleDeepLink(getIntent());
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        if (intent == null) return;

        Uri data = intent.getData();
        if (data == null) {
            Log.d(TAG, "No deep link data. Launched from launcher.");
            tvData.setText("No deep link data (launched from app icon)");
            tvHost.setText("");
            tvPath.setText("");
            tvCode.setText("");
            tvRef.setText("");
            return;
        }

        Log.d(TAG, "Deep link: " + data);
        tvData.setText(data.toString());

        String host = data.getHost();
        String path = data.getPath();

        tvHost.setText(host != null ? "Host: " + host : "Host: -");
        tvPath.setText(path != null ? "Path: " + path : "Path: -");

        List<String> segments = data.getPathSegments();
        String code = null;
        if (segments != null && segments.size() >= 2 && "promo".equals(segments.get(0))) {
            code = segments.get(1);
        }

        tvCode.setText(code != null ? "Code: " + code : "Code: -");

        String ref = data.getQueryParameter("ref");
        tvRef.setText(ref != null ? "Ref: " + ref : "Ref: -");

        Log.d(TAG, "host=" + host + ", path=" + path + ", code=" + code + ", ref=" + ref);
    }
}