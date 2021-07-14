package com.example.administrator.ottodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Registered recipient

        App.getMainThreadBusInstance().register(this);

        mButton = (Button) findViewById(R.id.second);
        mTextView = (TextView) findViewById(R.id.receiver);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * recipient
     * @param o
     */
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
        //Unregister
        App.getMainThreadBusInstance().unregister(this);
    }
}
