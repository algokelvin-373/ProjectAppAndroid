package algokelvin.android09.intentserialphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtNoPhone = findViewById(R.id.edt_no_phone);
        Button btnToSerialPhone = findViewById(R.id.btn_go_serial_phone);

        btnToSerialPhone.setOnClickListener(v -> {
            String phone = edtNoPhone.getText().toString();
            Intent intentSerialPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
            startActivity(intentSerialPhone);
        });

    }
}