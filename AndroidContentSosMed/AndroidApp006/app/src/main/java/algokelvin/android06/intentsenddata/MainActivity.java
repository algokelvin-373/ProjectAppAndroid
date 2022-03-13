package algokelvin.android06.intentsenddata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String DATA_SEND = "DATA_SEND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText edtData = findViewById(R.id.edt_data);
        Button btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(v -> {
            String data = edtData.getText().toString();
            Intent toPage = new Intent(this, PageIntentActivity.class);
            toPage.putExtra(DATA_SEND, data);
            startActivity(toPage);
        });
    }
}