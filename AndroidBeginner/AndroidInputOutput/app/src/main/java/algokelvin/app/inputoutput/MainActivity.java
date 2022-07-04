package algokelvin.app.inputoutput;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import algokelvin.app.inputoutput.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setAction();
    }

    private void setAction() {
        binding.btnLogin.setOnClickListener(v -> {
            String dataName = binding.edtInputName.getText().toString();
            Intent intentShow = new Intent(this, ShowActivity.class);
            intentShow.putExtra("data_name", dataName);
            startActivity(intentShow);
        });
    }

}