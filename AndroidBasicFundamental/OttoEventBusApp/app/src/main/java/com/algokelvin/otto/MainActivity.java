package com.algokelvin.otto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.otto.bus.AppThreadBus;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Turn On Otto Event Bus in this activity
        AppThreadBus.getMainThreadBusInstance().register(this); // Register

        Button mButton = findViewById(R.id.btn_second_activity);
        mTextView = findViewById(R.id.receiver);
        mButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });
    }

    @Subscribe
    public void getMessage(Object o) {
        if (mTextView != null) {
            mTextView.setText((String) o);
            Toast.makeText(MainActivity.this, "first activity textview's content change", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Turn On Otto Event Bus in this activity after this activity is destroyed
        AppThreadBus.getMainThreadBusInstance().unregister(this); // Unregister
    }
}