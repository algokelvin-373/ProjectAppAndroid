package algokelvin.android06.intentsenddata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PageIntentActivity extends AppCompatActivity {
    private final String DATA_SEND = "DATA_SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_intent);

        String receiverData = getIntent().getStringExtra(DATA_SEND);

        TextView txtDataReceiver = findViewById(R.id.txt_data_receiver);
        txtDataReceiver.setText("You Input => " + receiverData);

    }
}