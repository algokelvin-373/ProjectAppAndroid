package algokelvin.android04.saveinstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String DATA_SAVED = "SAVED";
    private EditText edtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtData = findViewById(R.id.edt_data);
        TextView txtData = findViewById(R.id.txt_data);
        Button btnClick = findViewById(R.id.btn_click);

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString(DATA_SAVED);
            if (data != null)
                txtData.setText(getString(R.string.text_data, data));
        }

        btnClick.setOnClickListener(v -> {
            String data = edtData.getText().toString();
            if (!data.isEmpty()) {
                txtData.setText(getString(R.string.text_data, data));
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA_SAVED, edtData.getText().toString());
    }
}