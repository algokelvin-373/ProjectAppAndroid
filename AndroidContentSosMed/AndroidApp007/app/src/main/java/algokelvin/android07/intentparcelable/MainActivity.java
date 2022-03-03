package algokelvin.android07.intentparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String DATA_OBJECT = "DATA_OBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtName = findViewById(R.id.edt_name);
        EditText edtAddress = findViewById(R.id.edt_address);
        EditText edtJob = findViewById(R.id.edt_job);
        Button btnOk = findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String address = edtAddress.getText().toString();
            String job = edtJob.getText().toString();

            Person person = new Person(name, address, job);
            sendObjData(person);
        });

    }

    private void sendObjData(Person person) {
        Intent toPage = new Intent(this, PageIntentActivity.class);
        toPage.putExtra(DATA_OBJECT, person);
        startActivity(toPage);
    }

}