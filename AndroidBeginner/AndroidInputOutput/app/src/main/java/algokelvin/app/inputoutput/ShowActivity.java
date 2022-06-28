package algokelvin.app.inputoutput;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import algokelvin.app.inputoutput.databinding.ActivityShowBinding;

public class ShowActivity extends AppCompatActivity {

    private ActivityShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityShowBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}