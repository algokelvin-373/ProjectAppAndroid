package algokelvin.android07.intentparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PageIntentActivity extends AppCompatActivity {
    private final String DATA_OBJECT = "DATA_OBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_intent);

        Person person = getIntent().getParcelableExtra(DATA_OBJECT);

        TextView txtName = findViewById(R.id.txt_name);
        TextView txtAddress = findViewById(R.id.txt_address);
        TextView txtJob = findViewById(R.id.txt_job);

        txtName.setText("Name     : " + person.name);
        txtAddress.setText("Address  : " + person.address);
        txtJob.setText("Job      : " + person.job);

    }
}