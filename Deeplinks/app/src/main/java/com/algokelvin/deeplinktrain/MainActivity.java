package com.algokelvin.deeplinktrain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivityDeepLink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleDeepLink(getIntent()); // RUN DEBUG DEEP LINK DI SINI
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent CALLED");
        setIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        if (intent == null) return;

        Log.d(TAG, "action=" + intent.getAction());
        Log.d(TAG, "dataString=" + intent.getDataString());
        Log.d(TAG, "extras=" + intent.getExtras());

        Uri data = intent.getData();
        if (data == null) {
            Log.w(TAG, "DATA NULL (normal kalau buka dari icon/Run)");
            return;
        }

        Log.d(TAG, "URI=" + data.toString());

        // Example link: https://example.com/promo/ABC?ref=ig
        String host = data.getHost();             // example.com
        String path = data.getPath();             // /promo/ABC
        List<String> segments = data.getPathSegments(); // ["promo", "ABC"]

        if ("example.com".equals(host) && segments.size() >= 2) {
            String first = segments.get(0); // "promo"
            String code  = segments.get(1); // "ABC"

            if ("promo".equals(first)) {
                String ref = data.getQueryParameter("ref");
                Log.d(TAG, "Ref: "+ ref);

                // Navigate ke page promo (example Activity)
                /*Intent i = new Intent(this, PromoActivity.class);
                i.putExtra("code", code);
                i.putExtra("ref", ref);
                startActivity(i);*/
            } else {
                Log.e(TAG, "ERROR 2");
            }
        } else {
            Log.e(TAG, "ERROR 1");
        }
    }
}