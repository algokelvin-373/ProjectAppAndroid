package algokelvin.app.inputoutput;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import algokelvin.app.inputoutput.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}