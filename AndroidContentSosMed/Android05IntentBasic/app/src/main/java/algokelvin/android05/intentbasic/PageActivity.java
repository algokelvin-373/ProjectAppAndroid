package algokelvin.android05.intentbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        TextView msgData = findViewById(R.id.msg_data);
        msgData.setText(getString(R.string.msg_intent));

    }
}